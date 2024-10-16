package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import green.mtcoding.bookbox.comment.Comment;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookResponse {

    @Data
    public static class CateTabDTO {
        private List<CateDTO> cates = new ArrayList<>();
        private List<BookListDTO> books = new ArrayList<>();

        public CateTabDTO(List<Category> cateList, List<Book> bookList) {
            for (Category cate : cateList) {
                this.cates.add(new CateDTO(cate));
            }
            for (Book book : bookList) {
                this.books.add(new BookListDTO(book));
            }
        }
    }

    @Data
    public static class CateDTO {
        private String id;
        private String name;

        public CateDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }


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
    public static class BookListDTO {
        private String isbn13;
        private String title;
        private String author;
        private String publisher;
        private String description;
        private String cover;
        private String categoryId;

        public BookListDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.publisher = book.getPublisher();
            this.description = book.getDescription();
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
        private String description;
        private String cover;


        public clickCategoryDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.description = book.getDescription();
            this.cover = book.getCover();
        }
    }

    @Data
    public static class DetailDTO {
        //책 id1
        private String isbn13;
        //제목1
        private String title;
        //저자1
        private String author;
        //책 커버1
        private String cover;
        //출판사1
        private String publisher;
        //책 요약 1
        private String description;
        //성인1
        private boolean adult;
        //출판날짜? 등록 날짜? 1
        private String pubDate;
        //대여 상태1
        private boolean lendStatus;
        //예약 상태1
        private boolean reservationStatus;
        //예약 수1
        private int reservationCount;
        //카테고리 아이디1
        private String categoryId;
        //리뷰
        List<CommentDTO> comments = new ArrayList<>();
        //찜하기(좋아요) 아직 구현안됨

        @Data
        class CommentDTO {
            //댓글 id 1
            private Long id;
            //댓글 1
            private String content;
            //댓글 생성 시간1
            private Timestamp createdAt;
            //닉네임
            private String nick;
            private boolean isOwner;

            public CommentDTO(Comment comment, Long currentUserId) {
                this.id = comment.getId();
                this.content = comment.getContent();
                this.createdAt = comment.getCreatedAt();
                this.nick = comment.getUser().getNick();
                this.isOwner = false;
                if (currentUserId != null) {
                    if (comment.getUser().getId() == currentUserId) {
                        isOwner = true;
                    }
                }
            }
        }

        public DetailDTO(Book book, Long currentUserId) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.cover = book.getCover();
            this.publisher = book.getPublisher();
            this.description = book.getDescription();
            this.adult = book.isAdult();
            this.pubDate = book.getPubDate();
            this.lendStatus = book.isLendStatus();
            this.reservationStatus = book.isReservationStatus();
            this.reservationCount = book.getReservationCount();
            this.categoryId = book.getCategory().getId();

            for (Comment comment : book.getComments()) {
                comments.add(new CommentDTO(comment, currentUserId));
            }
        }
    }

    @Data
    public static class DetailOnlyDTO {
        //책 id1
        private String isbn13;
        //제목1
        private String title;
        //저자1
        private String author;
        //책 커버1
        private String cover;
        //출판사1
        private String publisher;
        //책 요약 1
        private String description;
        //성인1
        private boolean adult;
        //출판날짜? 등록 날짜? 1
        private String pubDate;
        //대여 상태1
        private boolean lendStatus;
        //예약 상태1
        private boolean reservationStatus;
        //예약 수1
        private int reservationCount;
        //카테고리 아이디1
        private String categoryId;
        //좋아요?


        public DetailOnlyDTO(Book book) {
            this.isbn13 = book.getIsbn13();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.cover = book.getCover();
            this.publisher = book.getPublisher();
            this.description = book.getDescription();
            this.adult = book.isAdult();
            this.pubDate = book.getPubDate();
            this.lendStatus = book.isLendStatus();
            this.reservationStatus = book.isReservationStatus();
            this.reservationCount = book.getReservationCount();
            this.categoryId = book.getCategory().getId();
        }
    }

    @Data
    public static class CommentOnlyDTO {
        //댓글 id 1
        private Long id;
        //댓글 1
        private String content;
        //댓글 생성 시간1
        private Timestamp createdAt;
        //닉네임
        private String nick;
        private Long userId;
        private boolean isOwner;

        public CommentOnlyDTO(Comment comment, Long currentUserId) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.nick = comment.getUser().getNick();
            this.userId = comment.getUser().getId();
            this.isOwner = false;
            if (userId == currentUserId) {
                isOwner = true;
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
