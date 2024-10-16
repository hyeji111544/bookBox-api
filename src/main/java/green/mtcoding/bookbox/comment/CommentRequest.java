package green.mtcoding.bookbox.comment;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import lombok.Data;

public class CommentRequest {

    @Data
    public static class SaveDTO {
        private String isbn13;
        private String content;

        public Comment toEntity(Book book, User user) {
            return Comment.builder()
                    .content(content)
                    .book(book)
                    .user(user)
                    .build();
        }
    }
}
