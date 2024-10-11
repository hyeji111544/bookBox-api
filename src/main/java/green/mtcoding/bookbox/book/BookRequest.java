package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import lombok.Data;

public class BookRequest {

    // TODO: 도서 CRUD - 신민재
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

        public Book toEntity(Category category) {
            return new Book(isbn13, title, author, publisher, description, cover, pubDate, category);
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
        private String categoryId; // 카테고리 ID 포함

        // 만약 DTO에 추가적인 유효성 검증이 필요하면 @NotBlank 등 추가 가능
    }

}
