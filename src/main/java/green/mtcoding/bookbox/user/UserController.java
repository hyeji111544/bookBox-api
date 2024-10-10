package green.mtcoding.bookbox.user;


import green.mtcoding.bookbox.core.global.CommonResponse;
import green.mtcoding.bookbox.core.util.Resp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, Errors errors) {
        UserResponse.LoginDTO result = userService.로그인(loginDTO);
        String accessToken = result.getAccessToken();

        return ResponseEntity.ok()
                .header("Authorization", "Bearer "+ accessToken)
                .body(Resp.ok(result));
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @ModelAttribute UserRequest.JoinDTO joinDTO, Errors errors, @RequestParam("profile") MultipartFile profile){

/*
        , @RequestHeader("Authorization") String accessToken
        if (accessToken.startsWith("Bearer ")) {

            accessToken = accessToken.substring(7); // "Bearer " 이후 토큰 부분만 추출
        }

        // JWT 유효성 검증 로직 (유효한지, 만료되지 않았는지 등)
        User user = JwtUtil.verify(accessToken);
        //String username = claims.getSubject(); // JWT에서 유저명 추출*/

        UserResponse.JoinDTO model = userService.회원가입(profile, joinDTO);
        return ResponseEntity.ok(Resp.ok(model));
    }
}
