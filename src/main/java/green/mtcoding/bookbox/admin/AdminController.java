package green.mtcoding.bookbox.admin;

import green.mtcoding.bookbox.core.util.Resp;
import green.mtcoding.bookbox.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 로그인
    @PostMapping("/api/admins/login")
    public ResponseEntity<?> login(@RequestBody AdminRequest.LoginDTO loginDTO) {
        AdminResponse.LoginDTO result = adminService.로그인(loginDTO);
        String accessToken = result.getToken();

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .body(Resp.ok(result));
    }

    // 로그아웃
    @PostMapping("/api/admins/logout")
    public ResponseEntity<?> logout() {
        // JWT 토큰 삭제 방식
        return ResponseEntity.ok(Resp.ok("로그아웃이 완료되었습니다."));
    }

    // 전체 유저 목록 조회
    @GetMapping("/api/admins/user-list")
    public ResponseEntity<?> getUserList() {
        List<UserRequest.UserDTO> users = adminService.getUserList();
        return ResponseEntity.ok(Resp.ok(users));
    }
}
