package green.mtcoding.bookbox.like;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "like_tb")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn13", referencedColumnName = "isbn13")
    private Book book;

    @Builder
    public Like(Long id, User user, Book book) {
        this.id = id;
        this.user = user;
        this.book = book;
    }
}
