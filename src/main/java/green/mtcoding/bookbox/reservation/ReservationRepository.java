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

    // 예약 중복 확인
    boolean existsByUserAndBookAndCancelDateIsNull(User user, Book book);

    // 해당 도서에 대한 현재 예약수 조회 메소드
    @Query("select count(r) from Reservation r where r.book.isbn13 = :isbn13 and r.cancelDate is null")
    int countCurrentReservations(@Param("isbn13") String isbn13);

    // 유저와 도서로 예약 정보 찾기 (취소되지 않은 예약)
    Optional<Reservation> findByUserAndBookAndCancelDateIsNull(User user, Book book);

    // 예약 순번 업데이트
    @Modifying
    @Query("UPDATE Reservation r SET r.sequence = r.sequence - 1 WHERE r.book.isbn13 = :isbn13 AND r.sequence > :cancelledSequence")
    void updateReservationSequences(@Param("isbn13") String isbn13, @Param("cancelledSequence") int cancelledSequence);


    // 도서 예약 목록 조회
    @Query("select r from Reservation r where r.book.isbn13 = :isbn13 and r.cancelDate is null order by r.reservationDate asc")
    List<Reservation> findReservationsByBook(@Param("isbn13") String isbn13);

    // 유저별 예약 목록 조회
    List<Reservation> findByUser(User user);





    // 예약자 조회 쿼리
    @Query("select r.user.id from Reservation r where r.book.isbn13 = :isbn13")
    Long findUserIdByIsbn13(@Param("isbn13") String isbn13);

    @Modifying
    @Query("delete from Reservation r where r.user.id = :userId and r.book.isbn13 = :isbn13")
    void deleteByUserIdAndIsbn13(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    @Query("SELECT r.user.id FROM Reservation r WHERE r.book.isbn13 = :isbn13 ORDER BY r.id ASC")
    Long findFirstUserIdByIsbn13(@Param("isbn13") String isbn13);
}
