package green.mtcoding.bookbox.category;


import green.mtcoding.bookbox.book.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "category_tb")
public class Category {

    @Id
    @Column(name = "category_id")
    private String id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Book> books;// = new ArrayList<>();

}
