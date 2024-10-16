package green.mtcoding.bookbox.like;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.book.BookRepository;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi401;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.lend.LendRequest;
import green.mtcoding.bookbox.lend.LendResponse;
import green.mtcoding.bookbox.user.User;
import green.mtcoding.bookbox.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    @Transactional
    public LikeResponse.SaveDTO 즐겨찾기추가(Long userId, LikeRequest.SaveDTO saveDTO) {

        // 조회
        User userPS = userRepository.findById(userId).orElseThrow(() -> new ExceptionApi401("인증되지 않았습니다."));
        Book bookPS = bookRepository.findById(saveDTO.getIsbn13()).orElseThrow(() -> new ExceptionApi404("책 정보를 찾을 수 없습니다."));

        // 이미 즐겨찾기에 추가한 건지 조회
        // 조회 결과가 있을경우(즐찾추가한경우) 예외로 던진다.
        likeRepository.mFindByUserIdAndIsbn13(userPS.getId(), bookPS.getIsbn13()).ifPresent(like -> {
            throw new ExceptionApi404("이미 즐겨찾기에 추가된 도서입니다.");
        });

        // 저장
        Like likePS = likeRepository.save(saveDTO.toEntity(userPS, bookPS));

        return new LikeResponse.SaveDTO(likePS);
    }

    public LikeResponse.ListDTO 즐겨찾기목록조회(Long userId){

        // 조회
        User userPS = userRepository.findById(userId).orElseThrow(() -> new ExceptionApi401("인증되지 않았습니다."));

        // 즐겨찾기 목록 조회
        List<Like> likesPS = likeRepository.mFindAllByUserId(userId);

        // 즐겨찾기 추가 한적 없음
        if(likesPS.isEmpty()){
           throw new ExceptionApi400("즐겨찾기 목록이 없습니다.");
        }

        return new LikeResponse.ListDTO(likesPS);
    
    }

    @Transactional
    public void 즐겨찾기삭제(Long userId, LikeRequest.DeleteDTO deleteDTO){

        // 조회
        User userPS = userRepository.findById(userId).orElseThrow(() -> new ExceptionApi401("인증되지 않았습니다."));
        Book bookPS = bookRepository.findById(deleteDTO.getIsbn13()).orElseThrow(() -> new ExceptionApi404("책 정보를 찾을 수 없습니다."));

        // 즐겨찾기에 있는지 조회
        Like likePS = likeRepository.mFindByUserIdAndIsbn13(userPS.getId(), bookPS.getIsbn13()).orElseThrow(() -> new ExceptionApi400("즐겨찾기 목록에서 찾을 수 없습니다."));

        //즐겨찾기에서 삭제
        likeRepository.deleteById(likePS.getId());

    }


}
