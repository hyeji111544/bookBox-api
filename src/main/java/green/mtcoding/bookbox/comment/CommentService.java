package green.mtcoding.bookbox.comment;


import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.book.BookRepository;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi403;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void 리뷰삭제(Long id, HttpServletRequest request) {

        //1. 리뷰 존재 유무
        Comment commentPS = commentRepository.findById(id)
                .orElseThrow(() -> new ExceptionApi404("리뷰를 찾을 수 없습니다"));

        //2. HttpServletRequest에서 Authorization헤더 에서 JWT추출
        String token = JwtUtil.extractToken(request);
        Long userId = JwtUtil.extractUserIdFromToken(token);

        if(commentPS.getUser().getId() != userId){
            throw new ExceptionApi403("리뷰 삭제 권한이 없습니다");
        }

        //3. 리뷰 삭제
        commentRepository.deleteById(id);
    }

    @Transactional
    public CommentResponse.DTO 리뷰쓰기(CommentRequest.SaveDTO saveDTO, HttpServletRequest request) {

        //게시글 존재 유무 확인
        Book bookPS = bookRepository.findById(saveDTO.getIsbn13())
                .orElseThrow(()-> new ExceptionApi404("책을 찾을 수 없습니다."));

        //토큰으로 유저 정보 찾기
        String token = JwtUtil.extractToken(request);
        User user = JwtUtil.verify(token);

        //2. 비영속 댓글 객체 만들기
        Comment comment = saveDTO.toEntity(bookPS, user);

        //3. 댓글 저장(comment가 영속화된다)
        commentRepository.save(comment);
        return new CommentResponse.DTO(comment);
    }
}
