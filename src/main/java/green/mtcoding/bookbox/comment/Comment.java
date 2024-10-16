package green.mtcoding.bookbox.comment;


import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "comment_tb")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Comment(Long id, String content, Timestamp createdAt, Book book, User user) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.book = book;
        this.user = user;
    }
}
