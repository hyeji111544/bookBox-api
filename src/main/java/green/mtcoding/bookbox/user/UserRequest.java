package green.mtcoding.bookbox.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UserRequest {

    @Data
    @Setter
    @Getter
    @NoArgsConstructor
    public static class JoinDTO {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
        @NotEmpty
        private String nick;
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
        private String email;
        @NotEmpty
        private String phone;

        public User toEntity() {return User.builder().username(username).password(password).nick(nick).email(email).phone(phone).build();}

    }

    @Data
    public static class LoginDTO {
        @NotEmpty
        @Size(min = 2, max = 4)
        private String username;
        @NotEmpty
        private String password;
    }

    // TODO: 유저의 기본 정보와 필요 데이터 응답을 위해 생성 - 신민재
    @Data
    public static class UserDTO {
        private Long id;
        private String username;
        private String email;
        private String phone;


        // 매개변수로 받음
        public UserDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.phone = user.getPhone();
        }
    }

}
