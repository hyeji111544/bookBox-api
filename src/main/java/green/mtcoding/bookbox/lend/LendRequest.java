package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class LendRequest {


    @Data
    public static class SaveDTO {

        @NotEmpty
        private String isbn13;

        public Lend toEntity(User userPS, Book bookPS) {
            return Lend.builder().user(userPS).book(bookPS).build();
        }
    }


    @Data
    public static class ExtendDTO {

        @NotEmpty
        private String isbn13;
    }


}
