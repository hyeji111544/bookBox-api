package green.mtcoding.bookbox.admin;

import lombok.Data;

public class AdminRequest {

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }

    @Data
    public static class AdminDTO {
        private String username;
        private String email;
        private String phone;
        private String role;
    }
}
