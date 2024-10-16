package green.mtcoding.bookbox.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import green.mtcoding.bookbox.lend.LendResponse;
import green.mtcoding.bookbox.reservation.ReservationResponse;
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


    // TODO: 유저의 기본 정보와 필요 데이터 응답을 위해 생성 - 신민재
    // 대여, 예약 목록 추가
    @Data
    public static class UserDTO {
        private Long id;
        private String username;
        private String nick;
        private String email;
        private String phone;
        private List<LendResponse.LendDTO> lends;
        private List<ReservationResponse.ReservationDTO> reservations;

        public UserDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nick = user.getNick();
            this.email = user.getEmail();
            this.phone = user.getPhone();
            this.lends = user.getLends().stream().map(LendResponse.LendDTO::new).collect(Collectors.toList());
            this.reservations = user.getReservations().stream().map(ReservationResponse.ReservationDTO::new).collect(Collectors.toList());
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
