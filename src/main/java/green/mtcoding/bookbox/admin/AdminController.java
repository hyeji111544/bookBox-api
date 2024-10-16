package green.mtcoding.bookbox.admin;

import green.mtcoding.bookbox.book.BookRequest;
import green.mtcoding.bookbox.book.BookResponse;
import green.mtcoding.bookbox.book.BookService;
import green.mtcoding.bookbox.core.util.Resp;
import green.mtcoding.bookbox.user.UserRequest;
import green.mtcoding.bookbox.user.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final BookService bookService;

    // =========================== AUTH ====================================

//    // 자동 로그인
//    @PostMapping("/api/admins/auto/login")
//    public ResponseEntity<?> autoLogin(HttpServletRequest request) {
//        String accessToken = request.getHeader("Authorization");
//        AdminResponse.LoginDTO responseDTO = adminService.자동로그인(accessToken);
//        return ResponseEntity.ok(Resp.ok(responseDTO));
//    }

    // 로그인
    @PostMapping("/api/admins/login")
    public ResponseEntity<?> login(@RequestBody AdminRequest.LoginDTO loginDTO) {
        AdminResponse.LoginDTO result = adminService.로그인(loginDTO);
        String accessToken = result.getToken(); // 토큰 생성

        // JWT 토큰을 헤더에 포함시켜서 응답
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .body(Resp.ok("성공적으로 로그인 되었습니다."));
    }

    // 로그아웃
    @PostMapping("/api/admins/logout")
    public ResponseEntity<?> logout() {
        // JWT 토큰 삭제 방식
        return ResponseEntity.ok(Resp.ok("로그아웃이 완료되었습니다."));
    }

    // 전체 유저 목록 조회 (유저 정보만)
    @GetMapping("/api/admins/user-list")
    public ResponseEntity<?> getUserList() {
        List<UserResponse.UserDTO> users = adminService.getUserList();
        return ResponseEntity.ok(Resp.ok(users));
    }


    // 특정 유저의 예약 및 대여 목록 조회
    @GetMapping("/api/admins/user-detail/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long id) {
        UserResponse.UserDetailsDTO userDetails = adminService.getUserDetails(id);
        return ResponseEntity.ok(Resp.ok(userDetails));
    }




    // =========================== BOOK ====================================
    // 도서 등록
    @PostMapping("/api/admins/book-save")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest.SaveDTO saveDTO) {
        BookResponse.BookDetailDTO savedBook = bookService.도서등록(saveDTO);
        return ResponseEntity.ok(Resp.ok(savedBook));
    }

    // 도서 수정
    @PutMapping("/api/admins/{isbn13}")
    public ResponseEntity<?> updateBook(@PathVariable String isbn13, @RequestBody BookRequest.UpdateDTO updateDTO) {
        BookResponse.BookDetailDTO updateBook = bookService.도서업데이트(isbn13, updateDTO);
        return ResponseEntity.ok(Resp.ok(updateBook));
    }

    // 도서 삭제
    @DeleteMapping("/api/admins/{isbn13}/delete")
    public ResponseEntity<?> deleteBook(@PathVariable String isbn13) {
        bookService.deleteBook(isbn13);
        return ResponseEntity.ok(Resp.ok("도서가 성공적으로 삭제되었습니다."));
    }

    // 도서 상세보기 (등록 도서 조회)
    @GetMapping("/api/admins/{isbn13}")
    public ResponseEntity<?> getBookDetails(@PathVariable String isbn13) {
        BookResponse.BookDetailDTO bookDetail = bookService.도서상세보기(isbn13);
        return ResponseEntity.ok(Resp.ok(bookDetail));
    }
}
