package green.mtcoding.bookbox.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.user.id = :userId And l.book.isbn13 = :isbn13")
    Optional<Like> mFindByUserIdAndIsbn13(@Param("userId") Long userId, @Param("isbn13") String isbn13);

    // @Query("select l from Like l join fetch l.book b join fetch b.category where l.user.id = :userId")
    @Query("select l from Like l join fetch l.book b where l.user.id = :userId")
    List<Like> mFindAllByUserId(@Param("userId") Long userId);

}
