package green.mtcoding.bookbox.reservation;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReservationResponse {

    @Data
    public static class ReservationDTO {
        private Long id;
        private String bookTitle;
        private Timestamp reservationDate; // TODO: LocalDateTime에서 Timestamp로 변경했는데 2개의 차이
        private int sequence;


        public ReservationDTO(Reservation reservation) {
            this.id = reservation.getId();
            this.bookTitle = reservation.getBook().getTitle();
            this.reservationDate = reservation.getReservationDate();
            this.sequence = reservation.getSequence();

        }
    }
}
