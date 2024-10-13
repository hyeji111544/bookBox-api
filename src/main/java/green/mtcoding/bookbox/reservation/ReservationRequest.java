package green.mtcoding.bookbox.reservation;

import lombok.Data;

public class ReservationRequest {

    @Data
    public static class ReservationDTO {
        private Long userId;
        private String isbn13;
    }

}
