package green.mtcoding.bookbox.like;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.lend.Lend;
import green.mtcoding.bookbox.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LikeRequest {

    @Data
    public static class SaveDTO {

        @NotEmpty
        private String isbn13;

        public Like toEntity(User userPS, Book bookPS) {

            return Like.builder().user(userPS).book(bookPS).build();
        }
    }

    @Data
    public static class DeleteDTO {

        @NotEmpty
        private String isbn13;

    }

}
