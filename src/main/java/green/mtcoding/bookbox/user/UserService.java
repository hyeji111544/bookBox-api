package green.mtcoding.bookbox.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse.JoinResponse joinUser(UserRequest.JoinDTO request) {
        // 회원가입 로직
        User user = request.toEntity();
        userRepository.save(user);

        // JoinResponse 반환
        return UserResponse.JoinResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }


    public UserResponse.LoginResponse login(UserRequest.LoginDTO request) {
        // 입력된 username과 password를 검증
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) {
            throw new RuntimeException("로그인 실패");
        }

        // UserLoginResponse 반환
        return UserResponse.LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public UserResponse.CheckResponse getUser(Long id) {
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
    }
}
