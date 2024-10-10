package green.mtcoding.bookbox.admin;

import lombok.Builder;
import lombok.Data;

public class AdminResponse {

    @Data
    @Builder
    public static class AdminInfo {
        private Long id;
        private String username;
        private String email;
        private String phone;
        private String role;
    }

    @Data
    @Builder
    public static class LoginResponse {
        private Long id;
        private String username;
        private String token; // JWT 토큰
    }
}
