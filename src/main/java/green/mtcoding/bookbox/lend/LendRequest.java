package green.mtcoding.bookbox.lend;

import lombok.Data;

public class LendRequest {

    @Data
    public static class DTO {
        private String bookId;
    }
}
