package green.mtcoding.bookbox.book;


import green.mtcoding.bookbox.comment.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private String isbn13;

    private String title;
    private String author;
    private String publisher;
    private String description;
    private String categoryId;
    private String categoryName;
    private Integer itemId;
    private String cover;
    private String pubDate;
    private int stock;

    private boolean rentStatus;
    private boolean adult;

    @OneToMany(mappedBy = "book")
    private List<Comment> comments = new ArrayList<>();




}
