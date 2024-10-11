package green.mtcoding.bookbox.lend;


import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "lend_tb")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Timestamp lendDate;
    @Column(nullable = true)
    private Timestamp returnDate;

    private boolean returnStatus;
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


}
