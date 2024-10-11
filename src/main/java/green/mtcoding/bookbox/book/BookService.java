package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

//    public BookResponse.BookSearchDTO 검색기록보기(String title, String author, String publisher){
//
//        if(title == null || author == null || publisher == null){
//            List<Book> bookPG = bookRepository.findAll();
//            return new BookResponse.BookSearchDTO(bookPG, "");
//        }
//        List<Book> searchBookList = bookRepository.mFindAll(title, author, publisher);
//    }
    public List<BookResponse.BookListDTO> 메인책목록보기(){
        List<Book> books = bookRepository.mFindAllWithCategory();
        List<BookResponse.BookListDTO> dtos = new ArrayList<>();
        for(Book book : books) {
            BookResponse.BookListDTO dto = new BookResponse.BookListDTO(book);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<BookResponse.CategoryDTO> 책과카테고리보기(){
        List<Category> categories = bookRepository.mFindAllWithCategoryV2();
        List<BookResponse.CategoryDTO> dtos = new ArrayList<>();
        for(Category category : categories) {
            BookResponse.CategoryDTO dto = new BookResponse.CategoryDTO(category);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<BookResponse.clickCategoryDTO> 카테고리별책보기(String id){
        List<Book> bookList = bookRepository.mFindByCategoryId(id);
        List<BookResponse.clickCategoryDTO> dtos = new ArrayList<>();
        for(Book book : bookList) {
            BookResponse.clickCategoryDTO dto = new BookResponse.clickCategoryDTO(book);
            dtos.add(dto);
        }
        return dtos;
    }
}
