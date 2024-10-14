package green.mtcoding.bookbox.user;


import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.core.util.Resp;
import jakarta.validation.Valid;
import lombok.Getter;
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
                .header("Authorization", "Bearer " + accessToken)
                .body(Resp.ok(result, "성공적으로 로그인되었습니다."));
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO joinDTO, Errors errors) {

        UserResponse.JoinDTO result = userService.회원가입(joinDTO);
        return ResponseEntity.ok(Resp.ok(result, "회원가입이 완료되었습니다."));
    }

    // 회원탈퇴
    // 무작정 delete 하는게 아니라 다른 테이블에 저장하거나 탈퇴회원 표시를 해서 관리해야할 것 같은데 일단은 삭제함.
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {


        return ResponseEntity.ok(Resp.ok("탈퇴처리 되었습니다."));
    }

    //TODO : 없어진 기능
    // 회원 정보 수정
    @PutMapping("/api/users")
    public ResponseEntity<?> updateUserInfo(@RequestHeader("Authorization") String token, @ModelAttribute UserRequest.UpdateUserDTO updateUserDTO, @RequestParam(value = "profile", required = false) MultipartFile profile) {
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        UserResponse.UpdateInfoDTO result = userService.회원정보수정(userId, updateUserDTO, profile);

        return ResponseEntity.ok(Resp.ok(result, "회원정보가 수정되었습니다."));

    }

    // 닉네임 변경
    @PutMapping("/api/users/nick")
    public ResponseEntity<?> nickUpdate(@RequestHeader("Authorization") String token, @RequestBody UserRequest.UpdateNickDTO updateNickDTO) {
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        UserResponse.UpdateNickDTO result = userService.닉네임변경(userId, updateNickDTO);

        return ResponseEntity.ok(Resp.ok(result, "닉네임이 변경되었습니다."));
    }

    // 비밀번호 변경 만들어야 함



    @GetMapping("/users/one-username/{username}")
    public ResponseEntity<?> usernameDupCheck(@PathVariable("username") String username) {
        boolean emailDuplicate = userService.유저네임중복체크(username);

        if (emailDuplicate) {
            return ResponseEntity.ok(Resp.fail(400, "중복된 유저네임입니다."));
        } else {
            UserResponse.UsernameDupCheckDTO usernameDupCheckDTO = new UserResponse.UsernameDupCheckDTO(username);
            return ResponseEntity.ok(Resp.ok(usernameDupCheckDTO, "중복되지 않은 유저네임입니다."));
        }

    }

    @GetMapping("/users/one-nick/{nick}")
    public ResponseEntity<?> nickDupCheck(@PathVariable("nick") String nick) {

        boolean emailDuplicate = userService.닉네임중복체크(nick);


        if (emailDuplicate) {
            return ResponseEntity.ok(Resp.fail(400, "중복된 닉네임입니다."));
        } else {
            UserResponse.NickDupCheckDTO nickDupCheckDTO = new UserResponse.NickDupCheckDTO(nick);
            return ResponseEntity.ok(Resp.ok(nickDupCheckDTO, "중복되지 않은 닉네임입니다."));
        }

    }
}
