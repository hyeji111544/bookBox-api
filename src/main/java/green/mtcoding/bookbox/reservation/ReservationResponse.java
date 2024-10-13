package green.mtcoding.bookbox.reservation;

import lombok.Data;

import java.time.LocalDateTime;

public class ReservationResponse {

    @Data
    public static class ReservationDTO {
        private String bookTitle;
        private LocalDateTime reservationDate;
        private int sequence;

        public ReservationDTO(String bookTitle, LocalDateTime reservationDate, int sequence) {
            this.bookTitle = bookTitle;
            this.reservationDate = reservationDate;
            this.sequence = sequence;
        }
    }
}
