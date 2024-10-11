package green.mtcoding.bookbox.admin;

import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.user.User;
import green.mtcoding.bookbox.user.UserRepository;
import green.mtcoding.bookbox.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    // 로그인 로직
    public AdminResponse.LoginDTO 로그인(AdminRequest.LoginDTO request) {
        Admin admin = adminRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new ExceptionApi400("아이디 또는 비밀번호가 틀렸습니다."));

        // JWT 토큰 생성
        String token = JwtUtil.createAdminToken(admin);

        // 로그인 응답 반환
        return AdminResponse.LoginDTO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .token(token)
                .build();
    }


    // 관리자 정보 조회
    public AdminResponse.AdminInfo getAdminInfo(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ExceptionApi400("관리자를 찾을 수 없습니다."));

        return AdminResponse.AdminInfo.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .role(admin.getAdmin_role())
                .build();
    }

    // 전체 유저 목록 조회
    @Transactional
    public List<UserRequest.UserDTO> getUserList() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserRequest.UserDTO::new)
                .collect(Collectors.toList());
    }
}
