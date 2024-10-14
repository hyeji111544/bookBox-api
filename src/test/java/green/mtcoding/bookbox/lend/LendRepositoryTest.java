package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LendRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LendRepository lendRepository;

    @Test
    public void mFindBooksByUserId_test() {

        // given
        Long userId = 1L;
        // when
        List<Object[]> objects = lendRepository.mFindBooksByUserId(userId);
        // eye
        for (Object[] obj : objects) {
            String title = (String) obj[0];  // 첫 번째 값은 title
            String cover = (String) obj[1];  // 두 번째 값은 image

            System.out.println("Book title: " + title);
            System.out.println("Book cover: " + cover);
        }
    }

    @Test
    public void mCheckExtendStatus_test() {
        // given
        Long userId = 1L;
        String bookId = "9788937461798";

        // when
        Boolean b = lendRepository.mCheckExtendStatus(userId, bookId).orElseThrow(() -> new ExceptionApi404("대여정보가 없습니다."));
        System.out.println(b);
        // eye
        if (b.booleanValue()) {
            System.out.println("연장못함");
        } else {
            System.out.println("연장가능");
        }
    }

    @Test
    public void mExtendLend_test() {
        //given
        Long userId = 1L;
        String bookId = "9788937461798";
        //when
        lendRepository.mExtendLend(userId, bookId);
        //eye
        Boolean b = lendRepository.mCheckExtendStatus(userId, bookId).orElseThrow(() -> new ExceptionApi404("대여정보가 없습니다."));
        System.out.println(b);
        Lend lend = lendRepository.mFindLend(userId, bookId);
        System.out.println(lend.getLendDate());
    }

    @Test
    public void mFindLend_test() {
        //given
        Long userId = 1L;
        String bookId = "9791187011590";
        //when
        Lend lend = lendRepository.mFindLend(userId, bookId);
        //eye
        System.out.println(lend.getLendDate());
        System.out.println(lend.getId());
        System.out.println(lend.getBook().getTitle()); // 레이지로딩

    }

    @Test
    public void mReturnLend_test() {
        //given
        Long userId = 1L;
        String bookId = "9791187011590";

        //when
        lendRepository.mReturnLend(userId, bookId);
        //then
        Lend lend = lendRepository.mFindLend(userId, bookId);
        assertTrue(lend.isReturnStatus()); // 반납 상태가 true인지 확인
        assertNotNull(lend.getReturnDate()); // 반납 날짜가 제대로 설정되었는지 확인

    }

    @Test
    public void mFindLendsAndBooksByUserId_test() {
        //given
        Long userId = 1L;

        //when
        List<Object[]> results = lendRepository.mFindLendsAndBooksByUserId(userId);

        //eye
        for (Object[] result : results) {
            Lend lend = (Lend) result[0];
            Book book = (Book) result[1];

            System.out.println(lend.getId());
            System.out.println(book.getTitle());
        }


    }


    @Test
    public void mFindAllByReturnDateAndReturnStatusFalse_test() {
        //when
        List<Lend> lends = lendRepository.mFindAllByReturnDateAndReturnStatusFalse();

        //eye
        for (Lend lend : lends) {
            System.out.println(lend.isReturnStatus());
            System.out.println(lend.getBook().getTitle());
        }

    }

    @Test
    public void mAutoReturnLend_test2(){
        //given
        Long lendId = 1L;

        //when
        lendRepository.mAutoReturnLend(lendId);

        Lend lend = lendRepository.findById(lendId).orElseThrow(() -> new ExceptionApi404("대여 정보 찾을 수 없음"));

        //eye
        System.out.println(lend.getId());
        System.out.println(lend.getBook().getTitle());
        System.out.println(lend.getReturnDate());
        System.out.println(lend.isReturnStatus());
    }

    @Transactional
    @Test
    public void mAutoReturnLend_test() {

        //given
        List<Long> lendIds = new ArrayList<>();

        //when
        List<Lend> lends = lendRepository.mFindAllByReturnDateAndReturnStatusFalse(); // 반납해야 하는 대여건들
        System.out.println("반납해야 하는 대여건들 수: " + lends.size()); // 없으면 0임

        // 대여건들의 정보를 출력하고 lendIds 리스트에 추가
        for (Lend lend : lends) {
            System.out.println(lend.isReturnStatus()); // 반납 안 된 상태
            System.out.println(lend.getBook().getTitle()); // 데미안, 브람스 등의 책 제목
            lendIds.add(lend.getId());
        }

        // 각 lendId에 대해 반납 처리
        for (Long lendId : lendIds) {
            System.out.println("lendId: " + lendId);
            Integer i = lendRepository.mAutoReturnLend(lendId);
            System.out.println("이게 1이면 수정된 거임: " + i);
            lendRepository.flush(); // DB에 즉시 반영
            entityManager.clear();  // 영속성 컨텍스트 초기화
        }

        //then
        for (Long lendId : lendIds) {
            Lend lend = lendRepository.findById(lendId).orElseThrow(() -> new ExceptionApi404("대여 정보 찾을 수 없음"));
            System.out.println(lend.isReturnStatus());
            assertTrue(lend.isReturnStatus()); // 각 lend의 반납 상태가 true인지 확인
        }
    }

    @Test
    public void mFindLatestReturnedLend_test() {
        // given
        Long userId = 1L;
        String bookId = "9791187011590";

        // when: 가장 최근 반납된 대여 기록 조회
        Optional<Lend> latestLend = lendRepository.findLatestReturnedLendNative(userId, bookId);

        // then: 반환된 대여 기록이 가장 최근의 데이터인지 검증
        assertTrue(latestLend.isPresent());
        assertEquals(Timestamp.valueOf("2024-10-14 22:10:17.921182"), latestLend.get().getReturnDate());
        assertEquals(userId, latestLend.get().getUser().getId());
        assertEquals(bookId, latestLend.get().getBook().getIsbn13());
    }
}