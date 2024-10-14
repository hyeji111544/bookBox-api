package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import green.mtcoding.bookbox.comment.Comment;
import jakarta.persistence.*;
import lombok.Builder;
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

    // TODO: BookRequest.UpdateDTO를 받아서 엔티티를 업데이트하는 메소드
    public Book(String isbn13, String title, String author, String publisher, String description, String cover, String pubDate, Category category) {
        this.isbn13 = isbn13;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.cover = cover;
        this.pubDate = pubDate;
        this.category = category;
        this.lendStatus = false; // 대여 가능
    }

    public void update(BookRequest.UpdateDTO dto, Category category) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.publisher = dto.getPublisher();
        this.description = dto.getDescription();
        this.cover = dto.getCover();
        this.pubDate = dto.getPubDate();
        this.category = category; // 카테고리 업데이트
    }

    @Builder
    public Book(String isbn13, String title, String author, String publisher, String description, Integer itemId, String cover, String pubDate, boolean lendStatus, boolean adult, String link, boolean reservationStatus, int lendCount, int reservationCount, Category category) {
        this.isbn13 = isbn13;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.itemId = itemId;
        this.cover = cover;
        this.pubDate = pubDate;
        this.lendStatus = lendStatus;
        this.adult = adult;
        this.link = link;
        this.reservationStatus = reservationStatus;
        this.lendCount = lendCount;
        this.reservationCount = reservationCount;
        this.category = category;
    }
}