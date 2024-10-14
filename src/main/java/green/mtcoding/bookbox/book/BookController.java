package green.mtcoding.bookbox.book;


import green.mtcoding.bookbox.core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //책만 보이는 메인
    @GetMapping("/")
    public ResponseEntity<?> list() {
        List<BookResponse.BookListDTO> bookDTO = bookService.메인책목록보기();
        return ResponseEntity.ok(Resp.ok(bookDTO));
    }

    //책, 카테고리 다 보이는 메인
    @GetMapping("/api/main/book-list")
    public ResponseEntity<?> mainList() {
        List<BookResponse.CategoryDTO> categoryDTOS = bookService.메인책과카테고리목록보기();
        return ResponseEntity.ok(Resp.ok(categoryDTOS));
    }

    @GetMapping("/api/main/sort")
    public ResponseEntity<?> detail(@RequestParam(name = "id") String id) {
        List<BookResponse.clickCategoryDTO> bookListDTO = bookService.카테고리별책보기(id);
        return ResponseEntity.ok(Resp.ok(bookListDTO));
    }

    @GetMapping("/api/searches")
    public ResponseEntity<?> search(@RequestParam(name = "keyword") String keyword) {
        List<BookResponse.BookSearchDTO> searchDTOS = bookService.검색기록보기(keyword);
        return ResponseEntity.ok(Resp.ok(searchDTOS));
    }

}
