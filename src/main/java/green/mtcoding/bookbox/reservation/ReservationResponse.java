package green.mtcoding.bookbox.reservation;

import lombok.Data;

import java.time.LocalDateTime;

public class ReservationResponse {

    @Data
    public static class ReservationDTO {
        private String bookTitle;
        private LocalDateTime reservationDate;

        public ReservationDTO(String bookTitle, LocalDateTime reservationDate) {
            this.bookTitle = bookTitle;
            this.reservationDate = reservationDate;
        }
    }
}
