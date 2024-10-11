package green.mtcoding.bookbox.lend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class LendRepositoryTest {

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
        Boolean b = lendRepository.mCheckExtendStatus(userId, bookId);
        // then
        System.out.println(b);
        if(b.booleanValue()){
            System.out.println("연장했음");
        }else{
            System.out.println("연장안함");
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
        Boolean b = lendRepository.mCheckExtendStatus(userId, bookId);
        System.out.println(b);
    }

}
