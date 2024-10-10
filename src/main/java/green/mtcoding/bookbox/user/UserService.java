package green.mtcoding.bookbox.user;

import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi401;
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
    public UserResponse.JoinDTO 회원가입(MultipartFile profile, UserRequest.JoinDTO joinDTO) {

        String imageFileName = MyFile.파일저장(profile);

        Optional<User> userOP= userRepository.findByUsername(joinDTO.getUsername());
        if(userOP.isPresent()) {
            throw new ExceptionApi400("이미 존재하는 유저입니다.");
        }

        User userPS = userRepository.save(joinDTO.toEntity());

        userPS.setProfile(imageFileName);

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

/*    public UserResponse.CheckResponse getUser(Long id) {
        // ID로 사용자 조회
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자 없음"));

        // UserReadResponse 반환
        return UserResponse.CheckResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    public void updateUser(Long id, UserRequest.JoinDTO request) {
        // ID로 사용자 조회 후 정보 수정
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자 없음"));
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // 업데이트 된 사용자 저장
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        // ID로 사용자 삭제
        userRepository.deleteById(id);
    }*/
}
