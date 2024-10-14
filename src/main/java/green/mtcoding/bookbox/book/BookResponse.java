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
        private String keyword;

        public BookSearchDTO(Book book, String bookTitle) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.cover = book.getCover();
            this.keyword = bookTitle;
        }
    }


    @Data
    public static class CategoryDTO {
        private String id;
        private String name;

        List<BookListDTO> books = new ArrayList<>();

        @Data
        class BookListDTO {
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

        public CategoryDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();

            for (Book book : category.getBooks()) {
                books.add(new BookListDTO(book));
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

    // TODO: ===================== 도서 CRUD - 신민재 ===========================

    @Data
    public static class SaveDTO {
        private String isbn13;
        private String title;
        private String author;
        private String publisher;
        private String description;
        private String cover;
        private String pubDate;
        private String categoryId;

        public SaveDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.description = book.getDescription();
            this.cover = book.getCover();
            this.pubDate = book.getPubDate();
        }
    }

    @Data
    public static class UpdateDTO {
        private String title;
        private String author;
        private String publisher;
        private String description;
        private String cover;
        private String pubDate;
        private String categoryId;

        public UpdateDTO(Book book) {
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.description = book.getDescription();
            this.cover = book.getCover();
            this.pubDate = book.getPubDate();
            this.categoryId = book.getCategory().getId();
        }
    }


    @Data
    public static class BookDetailDTO {
        private String isbn13;
        private String title;
        private String author;
        private String publisher;
        private String description;
        private String cover;
        private String pubDate;
        private boolean lendStatus;

        public BookDetailDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.description = book.getDescription();
            this.cover = book.getCover();
            this.pubDate = book.getPubDate();
            this.lendStatus = book.isLendStatus();
        }
    }


}
