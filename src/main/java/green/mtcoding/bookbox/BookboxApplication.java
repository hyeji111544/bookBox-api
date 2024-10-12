package green.mtcoding.bookbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookboxApplication.class, args);
    }

}
