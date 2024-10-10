package green.mtcoding.bookbox.admin;


import green.mtcoding.bookbox.core.global.CommonResponse;
import green.mtcoding.bookbox.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/api/admins/login")
    public ResponseEntity<CommonResponse<AdminResponse.LoginResponse>> login(@RequestBody AdminRequest.LoginDTO request) {
        AdminResponse.LoginResponse response = adminService.login(request);
        return new ResponseEntity<>(CommonResponse.success(response), HttpStatus.OK);
    }

    @GetMapping("/api/admins/user-list")
    public ResponseEntity<CommonResponse<List<User>>> getUserList() {
        List<User> users = adminService.getUserList();
        return new ResponseEntity<>(CommonResponse.success(users), HttpStatus.OK);
    }

    // TODO: 추후 다른 crud 관련 API 추가 예정 (admin auth 마무리 하고)

}
