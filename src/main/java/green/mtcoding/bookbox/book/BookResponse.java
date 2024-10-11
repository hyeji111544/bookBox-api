package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class BookResponse {

    @Data
    public static class BookSearchDTO {
        private String isbn13;
        private String title;
        private String author;
        private String publisher;
        private String cover;

        public BookSearchDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.cover = book.getCover();
        }
    }

    @Data
    public static class BookListDTO {
        private String isbn13;
        private String title;
        private String author;
        private String cover;


        public BookListDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.cover = book.getCover();
        }
    }

    @Data
    public static class CategoryDTO {
        private String id;
        private String name;

        List<BookListDTO> bookList = new ArrayList<>();

        public CategoryDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();
            for(Book book : category.getBooks()) {
                bookList.add(new BookListDTO(book));
            }
        }
    }

    //카테고리 id로 찾을 항목
    @Data
    public static class clickCategoryDTO {
        private String isbn13;
        private String title;
        private String author;
        private String cover;


        public clickCategoryDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.cover = book.getCover();
        }
    }
}
