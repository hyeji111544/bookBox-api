package green.mtcoding.bookbox.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Username과 Password로 관리자 조회
    @Query("select a from Admin a where a.username = :username and a.password = :password")
    Optional<Admin> findByUsernameAndPassword(@Param("username") String username, String password);

    // 추가적으로 username으로만 조회할 경우 따로 정의 가능
    Optional<Admin> findByUsername(String username);
}
