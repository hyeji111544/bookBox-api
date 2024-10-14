package green.mtcoding.bookbox.user;


import green.mtcoding.bookbox.lend.Lend;
import green.mtcoding.bookbox.reservation.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    //TODO : 유저 닉네임 추가(주헌)
    @Column(nullable = false)
    private String nick;

    @NotBlank
    private String password;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(nullable = true)
    private Timestamp modifiedAt;

    //이미지 폴더 주소 보관 변수(이름 마음에 드는 걸로 변경 ㄱㄱ싱)
    private String profile;

    @OneToMany(mappedBy = "user")
    private List<Lend> lends = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    @Builder

    public User(Long id, String username, String nick, String password, String phone, String email, Timestamp createdAt, Timestamp modifiedAt, String profile) {
        this.id = id;
        this.username = username;
        this.nick = nick;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.profile = profile;
    }

    // 생성자 오버로딩
    public User(Long id){
        this.id = id;
    }

}
