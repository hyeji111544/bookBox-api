package green.mtcoding.bookbox.reservation;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "reservation_tb")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Timestamp reservationDate;
    private Timestamp cancelDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "isbn13")
    private Book book;

    @Column(nullable = false)
    private int sequence; // 예약 순번 추가

}
