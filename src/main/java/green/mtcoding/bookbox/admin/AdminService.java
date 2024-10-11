package green.mtcoding.bookbox.admin;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.book.BookRepository;
import green.mtcoding.bookbox.book.BookRequest;
import green.mtcoding.bookbox.book.BookResponse;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.user.User;
import green.mtcoding.bookbox.user.UserRepository;
import green.mtcoding.bookbox.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    // =========================== AUTH ====================================
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

    // 전체 유저 목록 조회
    @Transactional
    public List<UserRequest.UserDTO> getUserList() {
        List<User> users = userRepository.findAll();
        return users.stream() // TODO: Response로 변경
                .map(UserRequest.UserDTO::new)
                .collect(Collectors.toList());
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
