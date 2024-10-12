package green.mtcoding.bookbox.lend;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend, Long> {

    // 대여중인 도서 목록
    @Query("select l.book.isbn13, l.book.title, l.book.cover from Lend l Join l.book b where l.user.id = :userId AND l.returnStatus = false") // 조인 패치 유저
    List<Object[]> mFindBooksByUserId(@Param("userId") Long userId);

    // 도서 연장 했는지 확인
    @Query("select l.extendStatus from Lend l where l.user.id = :userId AND l.book.isbn13 = :isbn13 AND l.returnStatus = FALSE")
    Optional<Boolean> mCheckExtendStatus(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // 대여중인 도서 연장 (returnDate +7 & extendStatus = true)
    //@Query("update Lend l SET l.extendStatus = true, l.lendDate = FUNCTION('TIMESTAMPADD', 'DAY', 7, l.lendDate) where l.user.id = :userId AND l.book.isbn13 = :isbn13")
    @Modifying
    @Query(value = "UPDATE lend_tb SET extend_status = true, return_date = DATEADD('DAY', 7, return_date) WHERE user_id = :userId AND book_id = :isbn13", nativeQuery = true)
    void mExtendLend(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // 해당 user와 book의 lend 데이터 조회
    @Query("select l from Lend l where l.user.id = :userId AND l.book.isbn13 = :isbn13")
    Lend mFindLend(@Param("userId") Long userId, @Param("isbn13") String isbn13);
    
    // 대여중인 도서 반납 (returnStatus = true & returnDate = 현재 시간)
    @Modifying
    @Query(value = "UPDATE lend_tb SET return_status = true, return_date = CURRENT_TIMESTAMP WHERE user_id = :userId AND book_id = :isbn13 AND return_status = false", nativeQuery = true)
    Integer mReturnLend(@Param("userId") Long userId, @Param("isbn13") String isbn13);



/*
    // 자정 기준 반납처리할 도서 조회
  @Query("SELECT l FROM Lend l WHERE l.returnDate <= FUNCTION('DATEADD', 'DAY', -7, CURRENT_DATE) AND l.returnStatus = false")
  List<Lend> mFindAllByReturnDateAndReturnStatusFalse();
*/

}
