package green.mtcoding.bookbox.lend;


import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "lend_tb")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp lendDate;
    private Timestamp returnDate;

    @Column
    private boolean returnStatus;
    @Column
    private boolean extendStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "isbn13") // referencing the String id
    private Book book;

    @PrePersist
    public void prePersist() {
        this.returnStatus = false;
        this.extendStatus = false;

    }

    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setLendDate(Timestamp lendDate) {
        this.lendDate = lendDate;
    }
    @Builder
    public Lend(Long id, Timestamp lendDate, Timestamp returnDate, boolean returnStatus, boolean extendStatus, User user, Book book) {
        this.id = id;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.returnStatus = returnStatus;
        this.extendStatus = extendStatus;
        this.user = user;
        this.book = book;
    }

}
