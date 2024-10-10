package green.mtcoding.bookbox.user;

import lombok.Builder;
import lombok.Data;

import javax.swing.text.View;

public class UserResponse {

    @Data
    public static class JoinResponse {
        private Long id;
        private String username;
        private String email;

        @Builder
        public JoinResponse(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }
    }

    @Data
    public static class LoginResponse {
        private Long id;
        private String username;

        @Builder
        public LoginResponse(Long id, String username) {
            this.id = id;
            this.username = username;
        }
    }

    @Data
    public static class CheckResponse {
        private Long id;
        private String username;
        private String email;
        private String phone;

        @Builder
        public CheckResponse(Long id, String username, String email, String phone) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.phone = phone;
        }
    }
}
