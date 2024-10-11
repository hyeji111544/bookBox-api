package green.mtcoding.bookbox.book;


import green.mtcoding.bookbox.core.util.Resp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public ResponseEntity<?> list(){
        List<BookResponse.BookListDTO> bookDTO = bookService.메인책목록보기();
        return ResponseEntity.ok(Resp.ok(bookDTO));
    }

    @GetMapping("/2")
    public ResponseEntity<?> listV2(){
        List<BookResponse.CategoryDTO> categoryDTO = bookService.책과카테고리보기();
        return ResponseEntity.ok(Resp.ok(categoryDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") String id) {
        List<BookResponse.clickCategoryDTO> bookListDTO = bookService.카테고리별책보기(id);
        return ResponseEntity.ok(Resp.ok(bookListDTO));
    }
}
