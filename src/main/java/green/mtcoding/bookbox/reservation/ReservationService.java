package green.mtcoding.bookbox.reservation;


import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.book.BookRepository;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.lend.Lend;
import green.mtcoding.bookbox.lend.LendRepository;
import green.mtcoding.bookbox.user.User;
import green.mtcoding.bookbox.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LendRepository lendRepository;

    public Reservation 도서예약(Long userId, String isbn13) {
        // 현재 예약 인원 수 확인
        int currentReservationCount = reservationRepository.countCurrentReservations(isbn13);
        if (currentReservationCount >= 3) {
            throw new IllegalStateException("예약 인원이 다 찼습니다.");
        }

        // 예약 등록
        Book book = bookRepository.findByIsbn13(isbn13).orElseThrow(() -> new ExceptionApi400("도서정보를 찾을 수 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new ExceptionApi400("유저를 찾을 수 없습니다."));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(Timestamp.valueOf(LocalDateTime.now()));

        // 현재 예약 인원 수에 따라 sequence 설정 (1부터 카운트)
        reservation.setSequence(currentReservationCount + 1);

        return reservationRepository.save(reservation);
    }

    // 예약 취소
    public void 예약취소(Long userId, String isbn13) {
        // 유저와 도서 정보 로드
        User user = userRepository.findById(userId).orElseThrow(() -> new ExceptionApi400("유저를 찾을 수 없습니다."));
        Book book = bookRepository.findByIsbn13(isbn13).orElseThrow(() -> new ExceptionApi400("도서정보를 찾을 수 없습니다."));

        // 예약 정보 찾기 및 취소
        Reservation reservation = reservationRepository.findByUserAndBookAndCancelDateIsNull(user, book)
                .orElseThrow(() -> new ExceptionApi400("예약 정보를 찾을 수 없습니다."));

        reservation.setCancelDate(Timestamp.valueOf(LocalDateTime.now()));
        reservationRepository.save(reservation);

        // 예약 순번 업데이트
        List<Reservation> reservations = reservationRepository.findReservationsByBook(isbn13);
        int sequence = 1;
        for (Reservation r : reservations) {
            r.setSequence(sequence++);
            reservationRepository.save(r);
        }
    }

    // 예약 목록 조회 로직
    public List<ReservationResponse.ReservationDTO> 예약목록조회(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ExceptionApi400("유저를 찾을 수 없습니다."));
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream()
                .map(reservation -> new ReservationResponse.ReservationDTO(
                        reservation.getBook().getTitle(),
                        reservation.getReservationDate().toLocalDateTime()
                ))
                .collect(Collectors.toList());
    }

    // 반납 시 자동 대여 로직
    public void 자동대여(String isbn13) {
        Reservation firstReservation = reservationRepository.findReservationsByBook(isbn13)
                .stream()
                .findFirst()
                .orElse(null);

        if (firstReservation != null) {
            // 대여 처리
            Lend newLend = new Lend();
            newLend.setUser(firstReservation.getUser());
            newLend.setBook(firstReservation.getBook());
            lendRepository.save(newLend);

            // 예약 정보 삭제 또는 업데이트
            reservationRepository.delete(firstReservation);
        }
    }

}
