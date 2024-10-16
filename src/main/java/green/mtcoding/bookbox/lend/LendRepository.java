package green.mtcoding.bookbox.lend;


import green.mtcoding.bookbox.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend, Long> {

    // 대여중인 도서 목록
    @Query("select l.book.isbn13, l.book.title, l.book.cover, l.returnDate from Lend l Join l.book b where l.user.id = :userId AND l.returnStatus = false") // 조인 패치 유저
    List<Object[]> mFindBooksByUserId(@Param("userId") Long userId);

    // 도서 연장 했는지 확인
    @Query("select l.extendStatus from Lend l where l.user.id = :userId AND l.book.isbn13 = :isbn13 AND l.returnStatus = FALSE")
    Optional<Boolean> mCheckExtendStatus(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // 대여중인 도서 연장 (returnDate +7 & extendStatus = true)
    //@Query("update Lend l SET l.extendStatus = true, l.lendDate = FUNCTION('TIMESTAMPADD', 'DAY', 7, l.lendDate) where l.user.id = :userId AND l.book.isbn13 = :isbn13")
    @Modifying
    @Query(value = "UPDATE lend_tb SET extend_status = true, return_date = DATEADD('DAY', 7, return_date) WHERE user_id = :userId AND book_id = :isbn13", nativeQuery = true)
    void mExtendLend(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // 가장 최근에 반납한 도서 1개를 select 하거라
    @Query(value = "SELECT * FROM lend_tb WHERE user_id = :userId AND book_id = :isbn13 AND return_status = true ORDER BY return_date DESC LIMIT 1", nativeQuery = true)
    Optional<Lend> findLatestReturnedLendNative(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // 해당 user와 book의 lend 데이터 조회
    @Query("select l from Lend l where l.user.id = :userId AND l.book.isbn13 = :isbn13")
    Lend mFindLend(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // 대여중인 도서 반납 (returnStatus = true & returnDate = 현재 시간)
    @Modifying
    @Query(value = "UPDATE lend_tb SET return_status = true, return_date = CURRENT_TIMESTAMP WHERE user_id = :userId AND book_id = :isbn13 AND return_status = false", nativeQuery = true)
    Integer mReturnLend(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // 지금까지 대여한 도서 목록
    @Query("SELECT DISTINCT l, l.book FROM Lend l JOIN l.book b WHERE l.user.id = :userId")
    List<Object[]> mFindLendsAndBooksByUserId(@Param("userId") Long userId);

    // 오늘 날짜와 일치하면 자정 기준으로 반납 처리할 도서가 됨
    @Query("SELECT l FROM Lend l WHERE CAST(l.returnDate AS date) = CURRENT_DATE AND l.returnStatus = false")
    List<Lend> mFindAllByReturnDateAndReturnStatusFalse();

    // 반납일 도래한 도서 반납 (returnStatus = true & returnDate = 현재 시간)
    @Modifying
    @Query(value = "UPDATE lend_tb SET return_status = true, return_date = CURRENT_TIMESTAMP WHERE id = :lendId AND return_status = false", nativeQuery = true)
    Integer mAutoReturnLend(@Param("lendId") Long lendId);

    // 해당 유저가 지금 몇 권 대여한 상태인지 count
    @Query("select count(l) from Lend l where l.user.id = :userId And l.returnStatus = false")
    Long mFindCountByUserId(@Param("userId") Long userId);
}
