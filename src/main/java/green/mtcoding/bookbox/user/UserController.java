package green.mtcoding.bookbox.user;


import green.mtcoding.bookbox.core.global.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/join")
    public CommonResponse<UserResponse.JoinResponse> join(@RequestBody UserRequest.JoinDTO request ) {
        return CommonResponse.success(userService.joinUser(request));
    }

    @PostMapping("/users/login")
    public CommonResponse<UserResponse.LoginResponse> login(@RequestBody UserRequest.LoginDTO request ) {
        return CommonResponse.success(userService.login(request));
    }

    @GetMapping("/users/{id}")
    public CommonResponse<UserResponse.CheckResponse> getUser(@PathVariable Long id) {
        return CommonResponse.success(userService.getUser(id));
    }

    @PutMapping("/users/{id}")
    public CommonResponse<Void> updateUser(@PathVariable Long id, @RequestBody UserRequest.JoinDTO request) {
        userService.updateUser(id, request);
        return CommonResponse.success();
    }

    @DeleteMapping("/users/{id}")
    public CommonResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return CommonResponse.success();
    }
}
