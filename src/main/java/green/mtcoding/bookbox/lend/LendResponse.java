package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.book.Book;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LendResponse {

    //TODO: 자동반납 테스트용 (지우기)
    @Data
    public static class AutoReturnDTO {
        private Long lendId;
        private boolean returnStatus;
        private Timestamp returnDate;

        public AutoReturnDTO(List<Lend> lendPS) {
            for(Lend lend : lendPS) {
                this.lendId = lend.getId();
                this.returnStatus = lend.isReturnStatus();
                this.returnDate = lend.getReturnDate();
            }

        }
    }


    // 대여중인 도서목록
    @Data
    public static class ListDTO {

        private List<Book> books = new ArrayList<>();

        public ListDTO(List<Object[]> objectsPS) {
            for (Object[] objects : objectsPS) {
                String isbn13 = (String) objects[0];  // 첫 번째 값은 title
                String title = (String) objects[1];  // 두 번째 값은 image
                String cover = (String) objects[2];
                Timestamp returnDate = (Timestamp) objects[3];
                this.books.add(new Book(isbn13, title, cover, returnDate));
            }
        }

        @Data
        public static class Book {
            private String isbn13;
            private String title;
            private String cover;
            private Timestamp returnDate;


            public Book(String isbn13, String title, String cover, Timestamp returnDate) {
                this.isbn13 = isbn13;
                this.title = title;
                this.cover = cover;
                this.returnDate = returnDate;

            }
        }

    }


    // 방금 연장한 도서 정보 리턴
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

    // 방금 대여한 도서 정보 리턴
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

    // 방금 반납한 도서 정보
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

    // 총 대여 내역
    // 대여 일자, 반납일자, ISBN13, 책사진, 책제목
    @Data
    public static class HistoryDTO {
        private Long lendId;
        private Timestamp lendDate;
        private Timestamp returnDate;
        private boolean returnStatus;
        private String isbn13;
        private String title;
        private String cover;

        public HistoryDTO(Lend lend, Book book) {
            this.lendId = lend.getId();
            this.lendDate = lend.getLendDate();
            this.returnDate = lend.getReturnDate();
            this.returnStatus = lend.isReturnStatus();
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.cover = book.getCover();
        }

    }
}
