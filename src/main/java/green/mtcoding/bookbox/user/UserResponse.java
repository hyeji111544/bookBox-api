package green.mtcoding.bookbox.user;

import lombok.Builder;
import lombok.Data;

import javax.swing.text.View;
import java.sql.Timestamp;

public class UserResponse {
    // 회원가입
    @Data
    public static class JoinDTO {
        private Long id;
        private String username;
        private String email;
        private String phone;
        private String profile;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.phone = user.getPhone();
            this.profile = user.getProfile();
        }
    }

    // 로그인
    @Data
    public static class LoginDTO {
        private Long id;
        private String username;
        private String accessToken;

        @Builder
        public LoginDTO(User user, String accessToken) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.accessToken = accessToken;
        }
    }

    @Data
    public static class UpdateInfoDTO {
        private Long id;
        private String username;
        private String nick;
        private String password;
        private String email;
        private String phone;
        private String profile;
        private Timestamp modifiedAt;
        private Timestamp createdAt;

        public UpdateInfoDTO(User userPS) {
            this.id = userPS.getId();
            this.username = userPS.getUsername();
            this.nick = userPS.getNick();
            this.password = userPS.getPassword();
            this.email = userPS.getEmail();
            this.phone = userPS.getPhone();
            this.profile = userPS.getProfile();
            this.modifiedAt = userPS.getModifiedAt();
            this.createdAt = userPS.getCreatedAt();
        }
    }

    @Data
    public static class UsernameDupCheckDTO {
        private String username;

        public UsernameDupCheckDTO(String username) {
            this.username = username;
        }
    }

    @Data
    public static class NickDupCheckDTO {
        private String nick;

        public NickDupCheckDTO(String nick) {
            this.nick = nick;
        }
    }

    @Data
    public static class UpdateNickDTO {
        private String nick;
        private Long userId;

        public UpdateNickDTO(User userPS) {
            this.userId = userPS.getId();
            this.nick = userPS.getNick();
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
