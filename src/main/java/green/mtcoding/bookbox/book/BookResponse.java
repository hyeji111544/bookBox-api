package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import green.mtcoding.bookbox.comment.Comment;
import lombok.Data;

import java.sql.Timestamp;
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

    //책만 보이는 DTO
    @Data
    public static class BookListDTO{
        private String isbn13;
        private String title;
        private String author;
        private String publisher;
        private String cover;
        private String categoryId;

        public BookListDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.cover = book.getCover();
            this.categoryId = book.getCategory().getId();
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

    @Data
    public static class DetailDTO {
        //책 id
        private String isbn13;
        //제목
        private String title;
        //저자
        private String author;
        //책 커버
        private String cover;
        //출판사
        private String publisher;
        //책 요약
        private String description;
        //성인
        private boolean adult;
        //출판날짜? 등록 날짜?
        private String pubDate;
        //대여 상태
        private boolean lendStatus;
        //예약 상태
        private boolean reservationStatus;
        //예약 수
        private int reservationCount;
        //댓글
        List<CommentDTO> comments = new ArrayList<>();
        //찜하기(좋아요) 아직 구현안됨


        @Data
        class CommentDTO {
            private Long id;
            private String content;
            private Timestamp createdAt;
            private String nick;
            private boolean isOwner;

            public CommentDTO(Comment comment) {
                this.id = comment.getId();
                this.content = comment.getContent();
                this.createdAt = comment.getCreatedAt();
                this.nick = comment.getUser().getNick();
                this.isOwner = false;
                //권한 체크 해야함 이거를 어디서 해야하는가?
            }
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
