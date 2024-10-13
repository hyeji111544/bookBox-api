package green.mtcoding.bookbox.reservation;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 해당 도서에 대한 현재 예약수 조회 메소드
    @Query("select count(r) from Reservation r where r.book.isbn13 = :isbn13 and r.cancelDate is null")
    int countCurrentReservations(@Param("isbn13") String isbn13);

    // 유저와 도서로 예약 정보 찾기 (취소되지 않은 예약)
    Optional<Reservation> findByUserAndBookAndCancelDateIsNull(User user, Book book);

    // 예약 순번 업데이트
    @Modifying
    @Query("UPDATE Reservation r SET r.sequence = r.sequence - 1 WHERE r.book.isbn13 = :isbn13 AND r.sequence > :currentSequence")
    void updateReservationSequences(@Param("isbn13") String isbn13, @Param("currentSequence") int currentSequence);

    // 도서 예약 목록 조회
    @Query("select r from Reservation r where r.book.isbn13 = :isbn13 and r.cancelDate is null order by r.reservationDate asc")
    List<Reservation> findReservationsByBook(@Param("isbn13") String isbn13);

    // 유저별 예약 목록 조회
    List<Reservation> findByUser(User user);

}
