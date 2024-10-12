package green.mtcoding.bookbox.user;

import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi401;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.core.util.MyFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO joinDTO) {

        //String imageFileName = MyFile.파일저장(profile);

        Optional<User> userOP= userRepository.findByUsername(joinDTO.getUsername());
        if(userOP.isPresent()) {
            throw new ExceptionApi400("이미 존재하는 유저입니다.");
        }

        User userPS = userRepository.save(joinDTO.toEntity());

        userPS.setProfile("nobody.png");

        return new UserResponse.JoinDTO(userPS);
    }



    public UserResponse.LoginDTO 로그인(UserRequest.LoginDTO loginDTO) {
        // 1. 해당 유저가 있는 조회
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> new ExceptionApi401("인증되지 않았습니다"));

        // 2. 조회가 되면, JWT 만들고 응답하기
        String accessToken = JwtUtil.createUserToken(user);

        return new UserResponse.LoginDTO(user, accessToken);
    }






/*    public User 회원탈퇴(Long id) {
        // 조회
        Optional<User> userOP = userRepository.findById(id);
        // 없으면 예외던지기
        if(userOP.isEmpty() || userOP.get().getId() == null) {
            throw new ExceptionApi404("유저를 찾을 수 없습니다.");
        }

        // ID로 사용자 삭제
        userRepository.deleteById(id);
        // 삭제한 유저 정보 반환
        User user = userOP.get();
        return user;
    }*/
}
