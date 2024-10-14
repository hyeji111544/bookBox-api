package green.mtcoding.bookbox.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("select u from User u where u.username=:username and u.password=:password")
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    boolean existsByUsername(String email);

    boolean existsByNick(String nick);

    @Modifying
    @Query("UPDATE User u SET u.nick = :nick, u.modifiedAt = CURRENT_TIMESTAMP WHERE u.id = :userId")
    Integer mUpdateNick(@Param("userId") Long userId, @Param("nick") String nick);
}
