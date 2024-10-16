package green.mtcoding.bookbox.reservation;

import green.mtcoding.bookbox.book.Book;
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



    @Data
    public static class ReservationListDTO {
        private String isbn13;
        private String cover;
        private String bookTitle;
        private LocalDateTime reservationDate;
        private int sequence;

        public ReservationListDTO(Book book, LocalDateTime reservationDate, int sequence) {
            this.bookTitle = book.getTitle();
            this.isbn13 = book.getIsbn13();
            this.cover = book.getCover();
            this.reservationDate = reservationDate;
            this.sequence = sequence;
        }
    }


}
