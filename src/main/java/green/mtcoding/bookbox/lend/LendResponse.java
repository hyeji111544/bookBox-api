package green.mtcoding.bookbox.lend;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LendResponse {

    @Data
    public static class ListDTO {

        private List<Book> books = new ArrayList<>();

        public ListDTO(List<Object[]> objectsPS) {
            for (Object[] objects : objectsPS) {
                String isbn13 = (String) objects[0];  // 첫 번째 값은 title
                String title = (String) objects[1];  // 두 번째 값은 image
                String cover = (String) objects[2];
                this.books.add(new Book(isbn13, title, cover));
            }
        }

        @Data
        public static class Book {
            private String isbn13;
            private String title;
            private String cover;

            public Book(String isbn13, String title, String cover) {
                this.isbn13 = isbn13;
                this.title = title;
                this.cover = cover;

            }
        }

    }


    // 연장한 도서
    @Data
    public static class ExtensionDTO {
        private Long lendId; // 대여 pk
        private String isbn13; // 연장한 도서의 isbn
        private Boolean returnStatus; // 반납상태
        private Timestamp returnDate; // +7된 반납 예정일
        private Boolean extendStatus; // 연장 상태

        public ExtensionDTO(Lend lendPS) {
            this.lendId = lendPS.getId();
            this.isbn13 = lendPS.getBook().getIsbn13();
            this.returnStatus = lendPS.isReturnStatus();
            this.returnDate = lendPS.getReturnDate();
            this.extendStatus = lendPS.isExtendStatus();
        }
    }

    // 대여 id + 추가로 대여한 날짜
    @Data
    public static class LendDTO {
        private Long lendId; // 대여 pk
        private Timestamp lendDate; // 대여한 일자
        private Timestamp returnDate;

        public LendDTO(Lend lendPS) {
            this.lendId = lendPS.getId();
            this.lendDate = lendPS.getLendDate();
            this.returnDate = lendPS.getReturnDate();
        }
    }

    // 반납한 도서 정보
    @Data
    public static class ReturnDTO {
        private Long lendId; // 반납된 대여의 pk
        private Timestamp returnDate; // 반납일
        private Boolean returnStatus; // 반납상태

        public ReturnDTO(Lend lendPS) {
            this.lendId = lendPS.getId();
            this.returnDate = lendPS.getReturnDate();
            this.returnStatus = lendPS.isReturnStatus();
        }
    }

}
