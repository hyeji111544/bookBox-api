package green.mtcoding.bookbox.book;
import green.mtcoding.bookbox.category.Category;
import green.mtcoding.bookbox.comment.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "book_tb")
@Getter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @Column(name = "isbn13")
    private String isbn13;

    private String title;
    private String author;
    private String publisher;

    @Lob //description이 길어지면 VarChar(255)로 감당이 안 되는 녀석도 있어서 Text로 변경. 책 소개 다 들고 오면 길이 길어지므로 Lob붙여서 Text로 컬럼 생성
    private String description;

    private Integer itemId;
    private String cover;
    private String pubDate;
    private boolean lendStatus;
    private boolean adult;

    private String link;
    private boolean reservationStatus;

    private int lendCount;
    private int reservationCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "book")
    private List<Comment> comments = new ArrayList<>();

}
