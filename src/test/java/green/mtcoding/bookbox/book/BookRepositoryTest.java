package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi500;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void mFindAll_test(){
        String title = "제";
        String author = "";
        String publisher = "";
    }

//    @Test
//    public void mFindAllWithCategory_test(){
//        List<Book> books = bookRepository.mFindAllWithCategory();
//        System.out.println(books.get(0).getCategory());
//    }
//    @Test
//    public void mFindAllWithCategoryV2_test(){
//        List<Category> categories = bookRepository.mFindAllWithCategoryV2();
//        System.out.println(categories.size());
//    }
//
//    @Test
//    public void mFindByCategoryId_test(){
//        String id = "170";
//        //Optional<Book> book = bookRepository.mFindByCategoryId(id);
//        //System.out.println(book.isPresent());
//        List<Book> books = bookRepository.mFindAllWithCategory();
//        System.out.println(books.get(0).getCategory());
//    }



    // 대여 상태인지 확인
    @Test
public void mCheckLendStatus_test(){

        //given
        String isbn13 = "9791187011590";

        //when
        Boolean b = bookRepository.mCheckLendStatus(isbn13).orElseThrow(() -> new ExceptionApi404("요청하신 도서가 존재하지 않습니다."));

        //eye
        System.out.println(b);
        if(b.booleanValue()){
            System.out.println("누군가 대여중임");
        }else{
            System.out.println("대여가능상태");
        }
    }
    // 대여 하기
    @Test
    public void mUpdateLendStatusAndCount_test(){
        //given
        String isbn13 = "9791187011590";

        //when
        Integer i = bookRepository.mUpdateLendStatusAndCount(isbn13);

        if(i==1){
            Optional<Book> bookPS = bookRepository.findById(isbn13);
            bookPS.ifPresent(System.out::println);
            System.out.println(bookPS.get().getLendCount());
            System.out.println(bookPS.get().isLendStatus());
        }else{
            throw new ExceptionApi500("업데이트 안됨");
        }
    }

    @Test
    public void mUpdateLendStatusAndCountReturn_test(){
        //given
        String isbn13 = "9791187011590";

        //when
        Integer i = bookRepository.mUpdateLendStatusAndCountReturn(isbn13);

        //eye
        if(i==1){
            Optional<Book> bookPS = bookRepository.findById(isbn13);
            bookPS.ifPresent(System.out::println);
            System.out.println(bookPS.get().getLendCount());
            System.out.println(bookPS.get().isLendStatus());
        }else{
            throw new ExceptionApi500("반납 처리중 문제발생");
        }
    }


}
