package green.mtcoding.bookbox.user;


import green.mtcoding.bookbox.core.util.Resp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //자동 로그인
    @PostMapping("/auto/login")
    public ResponseEntity<?> autoLogin(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        UserResponse.AutoLoginDTO responseDTO = userService.자동로그인(accessToken);
        return ResponseEntity.ok(Resp.ok(responseDTO));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, Errors errors) {
        UserResponse.LoginDTO result = userService.로그인(loginDTO);
        String accessToken = result.getAccessToken();

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .body(Resp.ok(result, "성공적으로 로그인되었습니다."));
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO joinDTO, Errors errors) {

/*
        , @RequestHeader("Authorization") String accessToken
        if (accessToken.startsWith("Bearer ")) {

            accessToken = accessToken.substring(7); // "Bearer " 이후 토큰 부분만 추출
        }

        // JWT 유효성 검증 로직 (유효한지, 만료되지 않았는지 등)
        User user = JwtUtil.verify(accessToken);
        //String username = claims.getSubject(); // JWT에서 유저명 추출*/

        UserResponse.JoinDTO result = userService.회원가입(joinDTO);
        return ResponseEntity.ok(Resp.ok(result, "회원가입이 완료되었습니다."));
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {


        return ResponseEntity.ok(Resp.ok("회원가입이 완료되었습니다."));
    }
}
