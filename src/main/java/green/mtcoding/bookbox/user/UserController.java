package green.mtcoding.bookbox.user;


import green.mtcoding.bookbox.core.global.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User", description = "User API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/join")
    @Operation(summary = "유저 회원가입", description = "유저가 회원가입을 합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "파라미터 오류")
    public CommonResponse<UserResponse.JoinResponse> join(@Parameter(description = "유저 회원가입 요청") @RequestBody UserRequest.JoinDTO request ) {
        return CommonResponse.success(userService.joinUser(request));
    }

    @PostMapping("/users/login")
    @Operation(summary = "유저 로그인", description = "유저가 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "파라미터 오류")
    public CommonResponse<UserResponse.LoginResponse> login(@Parameter(description = "유저 로그인 요청") @RequestBody UserRequest.LoginDTO request ) {
        return CommonResponse.success(userService.login(request));
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "파라미터 오류")
    public CommonResponse<UserResponse.CheckResponse> getUser(@Parameter(description = "유저 ID", required = true)@PathVariable Long id) {
        return CommonResponse.success(userService.getUser(id));
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "유저 정보 수정", description = "유저 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "파라미터 오류")
    public CommonResponse<Void> updateUser(
            @Parameter(description = "유저 ID", required = true) @PathVariable Long id,
            @Parameter(description = "유저 정보 수정 요청") @RequestBody UserRequest.JoinDTO request) {
        userService.updateUser(id, request);
        return CommonResponse.success();
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "유저 삭제", description = "유저를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "파라미터 오류")
    public CommonResponse<Void> deleteUser(
            @Parameter(description = "유저 ID", required = true) @PathVariable Long id) {
        userService.deleteUser(id);
        return CommonResponse.success();
    }
}
