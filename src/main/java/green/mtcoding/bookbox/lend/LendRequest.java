package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LendRequest {


    @Data
    public static class SaveDTO {

        @NotEmpty
        private String isbn13;

        public Lend toEntity(User userPS, Book bookPS) {

            // 현재 시간을 대여 날짜로 설정
            LocalDateTime now = LocalDateTime.now();
            Timestamp lendDate = Timestamp.valueOf(now);

            // 대여 날짜로부터 7일 후를 반납 날짜로 설정 ( 반납 예정일 )
            LocalDateTime returnDateTime = now.plusDays(7);
            Timestamp returnDate = Timestamp.valueOf(returnDateTime);

            return Lend.builder().user(userPS).book(bookPS).lendDate(lendDate).returnDate(returnDate).build();
        }
    }


    @Data
    public static class ExtendDTO {

        @NotEmpty
        private String isbn13;
    }

    @Data
    public static class ReturnDTO {

        @NotEmpty
        private String isbn13;
    }


}
