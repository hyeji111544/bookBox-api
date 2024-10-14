package green.mtcoding.bookbox.reservation;


import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.book.BookRepository;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.lend.Lend;
import green.mtcoding.bookbox.lend.LendRepository;
import green.mtcoding.bookbox.user.User;
import green.mtcoding.bookbox.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LendRepository lendRepository;

    // 도서 예약하기
    @Transactional
    public ReservationResponse.ReservationDTO 도서예약(Long userId, String isbn13) {
        // 유저 정보 로드
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionApi404("해당 유저를 찾을 수 없습니다."));

        // 도서 대여 상태 확인
        Book book = bookRepository.findById(isbn13).orElseThrow(() -> new ExceptionApi404("해당 도서를 찾을 수 없습니다."));
        if (book.isLendStatus()) {  // true면 도서가 반납된 상태로 예약 불가능
            throw new ExceptionApi400("이 도서는 대여 가능한 상태라서 예약이 불가능합니다.");
        }

        // 예약 중복 확인 (해당 유저가 이미 예약한 도서인지 체크)
        boolean isAlreadyReserved = reservationRepository.existsByUserAndBookAndCancelDateIsNull(user, book);
        if (isAlreadyReserved) {
            throw new ExceptionApi400("이미 예약한 도서입니다.");
        }

        // 예약 가능 인원 수 확인 (최대 3명)
        int currentReservationCount = reservationRepository.countCurrentReservations(isbn13);
        if (currentReservationCount >= 3) {
            throw new ExceptionApi400("예약 인원이 다 찼습니다. 예약이 불가능합니다.");
        }

        // 예약 등록
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(Timestamp.valueOf(LocalDateTime.now()));
        reservation.setSequence(currentReservationCount + 1); // 예약 순번 설정

        reservationRepository.save(reservation);

        return new ReservationResponse.ReservationDTO (
            reservation.getBook().getTitle(),
            reservation.getReservationDate().toLocalDateTime(),
            reservation.getSequence()
        );

    }


    // 예약 취소
    public void 예약취소(Long userId, String isbn13) {
        // 유저와 도서 정보 로드
        User user = userRepository.findById(userId).orElseThrow(() -> new ExceptionApi400("유저를 찾을 수 없습니다."));
        Book book = bookRepository.findByIsbn13(isbn13).orElseThrow(() -> new ExceptionApi400("도서정보를 찾을 수 없습니다."));

        // 예약 정보 찾기 및 취소 (취소되지 않은 예약 정보만 조회)
        Reservation reservation = reservationRepository.findByUserAndBookAndCancelDateIsNull(user, book)
                .orElseThrow(() -> new ExceptionApi400("예약 정보를 찾을 수 없습니다."));

        // 이미 취소된 예약에 대한 예외 발생
        if (reservation.getCancelDate() != null) {
            throw new ExceptionApi400("이미 취소된 예약건입니다.");
        }

        // 예약 취소 날짜 설정
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
                        reservation.getReservationDate().toLocalDateTime(),
                        reservation.getSequence()
                ))
                .collect(Collectors.toList());
    }

    // 반납 시 자동 대여 로직
    @Transactional
    public void 자동대여(String isbn13) {
        // 첫 번째 예약자 확인
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

            // 예약 정보 삭제
            reservationRepository.delete(firstReservation);

            // 예약 순번 업데이트
            reservationRepository.updateReservationSequences(isbn13, firstReservation.getSequence());
        }
    }

}
