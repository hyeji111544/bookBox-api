package green.mtcoding.bookbox.admin;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "admin_tb")
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, length = 100) // 암호화 상태를 고려해 넉넉하게 설정
    private String password;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(nullable = false, length = 20) // Admin, Super Admin
    private String admin_role;


    public Admin(long id, String username, String password, String email, String phone, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.admin_role = role;
    }
}
