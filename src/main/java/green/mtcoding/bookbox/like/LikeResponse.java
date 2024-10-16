package green.mtcoding.bookbox.like;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LikeResponse {
    
    // 즐찾 저장
    @Data
    public static class SaveDTO {
        private Long id;
        private Long userId;
        private String isbn13;

        public SaveDTO(Like likePS) {
            this.id = likePS.getId();
            this.userId = likePS.getUser().getId();
            this.isbn13 = likePS.getBook().getIsbn13();
        }
    }

    // 즐찾 조회
    @Data
    public static class ListDTO {
        private List<SingleLikeDTO> likes;
        
        // 반복문 대신에 stream map 사용해봄
        public ListDTO(List<Like> likes) {
            this.likes = likes.stream()
                    .map(SingleLikeDTO::new)
                    .collect(Collectors.toList());
        }

        @Data
        public static class SingleLikeDTO {
            private Long id; // 좋아요 식별키
            private Long userId;
            private String isbn13;
            private String categoryId;
            private String cover;
            private String title;
            private String author;
            private String description;
            private boolean lendStatus;
            private int reservationCount;

            public SingleLikeDTO(Like like) {
                this.id = like.getId();
                this.userId = like.getUser().getId();
                this.isbn13 = like.getBook().getIsbn13();
                this.categoryId = like.getBook().getCategory().getId();
                this.cover = like.getBook().getCover();
                this.title = like.getBook().getTitle();
                this.author = like.getBook().getAuthor();
                this.description = like.getBook().getDescription();
                this.lendStatus = like.getBook().isLendStatus();
                this.reservationCount = like.getBook().getReservationCount();
            }
        }
    }

    // 즐찾 삭제
    @Data
    public static class DeleteDTO {
        private Long id; // 삭제한 즐겨찾기 id
        private Long userId;
        private String isbn13;

        public DeleteDTO(Like likePS) {
            this.id = likePS.getId();
            this.userId = likePS.getUser().getId();
            this.isbn13 = likePS.getBook().getIsbn13();
        }
    }

}
