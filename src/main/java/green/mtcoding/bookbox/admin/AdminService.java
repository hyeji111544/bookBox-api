package green.mtcoding.bookbox.admin;


import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.user.User;
import green.mtcoding.bookbox.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public AdminResponse.LoginResponse login(AdminRequest.LoginDTO request) {
        // 로그인 로직
        Admin admin = adminRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new ExceptionApi400("아이디 또는 비밀번호가 틀렸습니다."));

        // JWT 토큰 생성
        String token = JwtUtil.createAdminToken(admin);

        // 로그인 응답 반환
        return AdminResponse.LoginResponse.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .token(token)
                .build();
    }

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
    public List<User> getUserList() {
        return userRepository.findAll();
    }


    // TODO: 추가적인 crud 기능도 여기 포함 (회원 관리, 도서 관리 등)
}
