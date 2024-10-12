package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class LendRepositoryTest {

/*
    @Autowired
    private LendRepository lendRepository;

    @Test
    public void mFindBooksByUserId_test(){

        // given
        Long userId = 1L;
        // when
        List<Object[]> objects = lendRepository.mFindBooksByUserId(userId);
        // then
        for (Object[] obj : objects) {
            String title = (String) obj[0];  // 첫 번째 값은 title
            String cover = (String) obj[1];  // 두 번째 값은 image

            System.out.println("Book title: " + title);
            System.out.println("Book cover: " + cover);
        }
    }

    @Test
    public void mCheckExtendStatus_test(){
        // given
        Long userId = 1L;
        String bookId = "9788937461798";

        // when
        Boolean b = lendRepository.mCheckExtendStatus(userId, bookId).orElseThrow(() -> new ExceptionApi404("대여정보가 없습니다."));
        System.out.println(b);
        // then
        if(b.booleanValue()){
            System.out.println("연장못함");
        }else{
            System.out.println("연장가능");
        }
    }

    @Test
    public void mExtendLend_test(){
        //given
        Long userId = 1L;
        String bookId = "9788937461798";
        //when
        lendRepository.mExtendLend(userId, bookId);
        //then
        Boolean b = lendRepository.mCheckExtendStatus(userId, bookId).orElseThrow(() -> new ExceptionApi404("대여정보가 없습니다."));
        System.out.println(b);
        Lend lend = lendRepository.mFindLend(userId, bookId);
        System.out.println(lend.getLendDate());
    }

    @Test
    public void mFindLend_test(){
        //given
        Long userId = 1L;
        String bookId = "9788937461798";
        //when
        Lend lend = lendRepository.mFindLend(userId, bookId);
        //then
        System.out.println(lend.getLendDate());
        System.out.println(lend.getId());
        System.out.println(lend.getBook().getTitle()); // 레이지로딩

    }
*/

}
