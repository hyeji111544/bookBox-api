package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
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

    @Test
    public void mFindAllWithCategory_test(){
        List<Book> books = bookRepository.mFindAllWithCategory();
        System.out.println(books.get(0).getCategory());
    }
    @Test
    public void mFindAllWithCategoryV2_test(){
        List<Category> categories = bookRepository.mFindAllWithCategoryV2();
        System.out.println(categories.size());
    }

    @Test
    public void mFindByCategoryId_test(){
        String id = "170";
        //Optional<Book> book = bookRepository.mFindByCategoryId(id);
        //System.out.println(book.isPresent());
    }
}
