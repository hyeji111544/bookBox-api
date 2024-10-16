package green.mtcoding.bookbox.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

public class UserResponse {
    // 회원가입
    @Data
    public static class JoinDTO {
        private Long id;
        private String username;
        private String nick;
        private String email;
        private String phone;
        private String profile;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nick = user.getNick();
            this.email = user.getEmail();
            this.phone = user.getPhone();
            this.profile = user.getProfile();
        }
    }

    // 자동 로그인
    record AutoLoginDTO(Long id, String username) {
        AutoLoginDTO(User user) {
            this(user.getId(), user.getUsername());
        }
    }



    // 로그인
    @Data
    public static class LoginDTO {
        private Long id;
        private String username;
        @JsonIgnore
        private String accessToken;

        @Builder
        public LoginDTO(User user, String accessToken) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.accessToken = accessToken;
        }
    }

    // 조회
    @Data
    public static class CheckResponse { // 조회
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
