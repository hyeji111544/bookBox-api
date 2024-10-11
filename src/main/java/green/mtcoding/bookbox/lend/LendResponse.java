package green.mtcoding.bookbox.lend;

import lombok.Data;

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
