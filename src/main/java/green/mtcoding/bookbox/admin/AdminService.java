package green.mtcoding.bookbox.admin;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.book.BookRepository;
import green.mtcoding.bookbox.book.BookRequest;
import green.mtcoding.bookbox.book.BookResponse;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.user.User;
import green.mtcoding.bookbox.user.UserRepository;
import green.mtcoding.bookbox.user.UserRequest;
import green.mtcoding.bookbox.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    // =========================== AUTH ====================================
//    // 자동 로그인 로직
//    public AdminResponse.LoginDTO 자동로그인(String accessToken) {
//
//        Optional.ofNullable(accessToken).orElseThrow(() -> new ExceptionApi400("토큰을 찾을 수 없습니다."));
//        try {
//            Admin admin = JwtUtil.verifyAdminToken(accessToken);
//            Admin adminPS = adminRepository.findById(admin.getId())
//                    .orElseThrow(() -> new ExceptionApi400("관리자를 찾을 수 없습니다."));
//            return AdminResponse.LoginDTO.builder()
//                    .id(adminPS.getId())
//                    .username(adminPS.getUsername())
//                    .build();
//        } catch (SignatureVerificationException | JWTDecodeException e1) {
//            throw new ExceptionApi400("유효하지 않은 토큰입니다.");
//        } catch (TokenExpiredException e2) {
//            throw new ExceptionApi400("토큰이 만료되었습니다.");
//        }
//    }

    // 로그인 로직
    public AdminResponse.LoginDTO 로그인(AdminRequest.LoginDTO request) {
        Admin admin = adminRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new ExceptionApi400("아이디 또는 비밀번호가 틀렸습니다."));

        // JWT 토큰 생성
        String token = JwtUtil.createAdminToken(admin);

        // 로그인 응답 반환
        return AdminResponse.LoginDTO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .token(token)
                .build();
    }


    // 관리자 정보 조회
    public AdminResponse.AdminInfo getAdminInfo(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ExceptionApi400("관리자를 찾을 수 없습니다."));

        return AdminResponse.AdminInfo.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .role(admin.getAdmin_role())
                .build();
    }

    // 전체 유저 목록 조회 (유저 정보만)
    @Transactional
    public List<UserResponse.UserDTO> getUserList() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponse.UserDTO::new)
                .collect(Collectors.toList());
    }

    // 특정 유저의 예약 및 대여 목록 조회
    @Transactional
    public UserResponse.UserDetailsDTO getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionApi400("해당 유저를 찾을 수 없습니다."));

        // Lazy 로딩 된 대여 및 예약 목록을 명시적으로 초기화
        Hibernate.initialize(user.getLends());
        Hibernate.initialize(user.getReservations());

        // 유저의 예약 및 대여 목록을 함께 반환
        return new UserResponse.UserDetailsDTO(user);
    }


//    // =========================== BOOK ====================================
//    // 도서 등록 로직
//    public BookResponse.BookListDTO 도서등록(BookRequest.SaveDTO dto) {
//        Book book = dto.toEntity();  // DTO를 통해 Entity 생성
//        bookRepository.save(book);
//        return new BookResponse.BookListDTO(book);
//    }
//
//    // 도서 수정 로직
//    public BookResponse.BookListDTO updateBook(String isbn13, BookRequest.UpdateDTO dto) {
//        Book book = bookRepository.findById(isbn13)
//                .orElseThrow(() -> new ExceptionApi400("도서를 찾을 수 없습니다."));
//
//        // 기존 데이터 업데이트
//        book.update(dto);
//        bookRepository.save(book);
//        return new BookResponse.BookListDTO(book);
//    }
//
//    // 도서 삭제 로직
//    public void deleteBook(String isbn13) {
//        bookRepository.deleteById(isbn13);
//    }
//
//    // 등록된 도서 상세보기
//    public BookResponse.BookDetailDTO 도서상세보기(String isbn13) {
//        Book book = bookRepository.findById(isbn13)
//                .orElseThrow(() -> new ExceptionApi400("도서를 찾을 수 없습니다."));
//
//        return new BookResponse.BookDetailDTO(book);
//    }

}
