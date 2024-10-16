package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import green.mtcoding.bookbox.category.CategoryRepository;
import green.mtcoding.bookbox.comment.Comment;
import green.mtcoding.bookbox.comment.CommentRepository;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;



    public List<BookResponse.BookSearchDTO> 검색기록보기(String keyword){
        System.out.println("검색어:" + keyword);
        //아무것도 적지 않았을 때
        if(keyword == null){
            List<Book> bookPG = bookRepository.findAll();
            List<BookResponse.BookSearchDTO> dtos = new ArrayList<>();
            for(Book book : bookPG){
                BookResponse.BookSearchDTO dto = new BookResponse.BookSearchDTO(book, "");
                dtos.add(dto);
            }
            return dtos;
        }
        List<Book> searchBookList = bookRepository.mFindAll(keyword);
        //만약 검색결과가 없을 때
        if(searchBookList.isEmpty()){
         throw new ExceptionApi404("검색 결과가 없습니다.");
        }
        //검색 결과가 있을 때
        List<BookResponse.BookSearchDTO> dtos = new ArrayList<>();
        for(Book book : searchBookList){
            BookResponse.BookSearchDTO dto = new BookResponse.BookSearchDTO(book, keyword);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<BookResponse.BookListDTO> 메인책목록보기(){
        List<Book> books = bookRepository.mFindBooksWithCategory();
        List<BookResponse.BookListDTO> dtos = new ArrayList<>();
        for(Book book : books) {
            BookResponse.BookListDTO dto = new BookResponse.BookListDTO(book);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<BookResponse.CategoryDTO> 메인책과카테고리목록보기(){
        List<Category> categories = bookRepository.mFindAllWithCategory();
        List<BookResponse.CategoryDTO> dtos = new ArrayList<>();
        for(Category category : categories){
            BookResponse.CategoryDTO dto = new BookResponse.CategoryDTO(category);
            dtos.add(dto);
        }
        return dtos;
    }






    public BookResponse.DetailDTO 책상세보기(String isbn13, HttpServletRequest request){
        Book bookPS = bookRepository.mFindByIdWithComment(isbn13)
                .orElseThrow(()-> new ExceptionApi404("해당 책이 없습니다"));

        String token = JwtUtil.extractToken(request);

        if(token == null){
            return new BookResponse.DetailDTO(bookPS,null);
        }
        Long userId = JwtUtil.extractUserIdFromToken(token);
        return new BookResponse.DetailDTO(bookPS,userId);
    }
    public BookResponse.DetailOnlyDTO 책만상세보기(String isbn13){
        Book bookPS = bookRepository.mFindByIsbn13(isbn13)
                .orElseThrow(() -> new ExceptionApi404("해당 책이 없습니다."));
        return new BookResponse.DetailOnlyDTO(bookPS);
    }

    public List<BookResponse.CommentOnlyDTO> 댓글만상세보기(String isbn13, HttpServletRequest request){
        String token = JwtUtil.extractToken(request);
        System.out.println("1");
        Long userId = JwtUtil.extractUserIdFromToken(token);
        List<Comment> commentPS = commentRepository.mFindCommentsByBookIsbn13(isbn13);
        System.out.println("2");
        List<BookResponse.CommentOnlyDTO> dtos = new ArrayList<>();
        System.out.println("3");
        for(Comment comment : commentPS){
            BookResponse.CommentOnlyDTO dto = new BookResponse.CommentOnlyDTO(comment,userId);
            dtos.add(dto);
        }
        return dtos;
    }



    public BookResponse.CateTabDTO 카테탭(){
        List<Book> books = bookRepository.mFindAll();
        List<Category> cates = categoryRepository.mFindAll();
        return new BookResponse.CateTabDTO(cates, books);
    }

    public List<BookResponse.BookListDTO> 업데이트순(){
        LocalDate threeMonth = LocalDate.now().minus(3, ChronoUnit.MONTHS);
        String formattedDate = threeMonth.format(DateTimeFormatter.ISO_DATE);
        List<Book> books = bookRepository.mFindAllPubDateDesc(formattedDate);
        List<BookResponse.BookListDTO> dtos = new ArrayList<>();
        int limit = Math.min(30, books.size());
        for (int i = 0; i < limit; i++) {
            Book book = books.get(i); // 인덱스를 사용하여 책을 가져옴
            BookResponse.BookListDTO dto = new BookResponse.BookListDTO(book);
            dtos.add(dto);
        }
        return dtos;
    }


    // TODO: AdminController 도서 CRUD 처리를 위한 로직 - 신민재
    // 도서 등록
    public BookResponse.BookDetailDTO 도서등록(BookRequest.SaveDTO dto) {
        // 카테고리 조회
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ExceptionApi400("카테고리를 찾을 수 없습니다."));

        Book book = dto.toEntity(category); // DTO -> Entity 변환
        bookRepository.save(book);
        return new BookResponse.BookDetailDTO(book);
    }

    // 도서 수정
    public BookResponse.BookDetailDTO 도서업데이트(String isbn13, BookRequest.UpdateDTO dto) {
        Book book = bookRepository.findById(isbn13)
                .orElseThrow(() -> new ExceptionApi400("도서를 찾을 수 없습니다."));

        // 카테고리 조회
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ExceptionApi400("카테고리를 찾을 수 없습니다."));

        // 도서 정보 업데이트
        book.update(dto, category);
        bookRepository.save(book);
        return new BookResponse.BookDetailDTO(book);
    }

    // 도서 삭제
    public void deleteBook(String isbn13) {
        bookRepository.deleteById(isbn13);
    }

    // 도서 상세보기
    public BookResponse.BookDetailDTO 도서상세보기(String isbn13) {
        Book book = bookRepository.findById(isbn13)
                .orElseThrow(() -> new ExceptionApi400("도서를 찾을 수 없습니다."));
        return new BookResponse.BookDetailDTO(book);
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
