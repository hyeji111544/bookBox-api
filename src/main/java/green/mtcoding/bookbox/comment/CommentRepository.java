package green.mtcoding.bookbox.comment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.book.isbn13 = :isbn13 order by c.createdAt DESC")
    List<Comment> mFindCommentsByBookIsbn13(@Param("isbn13") String isbn13);
}
