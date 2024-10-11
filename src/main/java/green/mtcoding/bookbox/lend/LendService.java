package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LendService {

    private final LendRepository lendRepository;

    public LendResponse.ListDTO 대여중도서목록조회(Long userId){

        List<Object[]> objectsPS = lendRepository.mFindBooksByUserId(userId);

        return new LendResponse.ListDTO(objectsPS); // 도서 cover, title, isbn13 -> list로!
    }
/*
    @Transactional
    public LendResponse 대여중인도서연장(Long userId, LendRequest.DTO request){
        // 연장 상태가 false 이면 true로 바꾸고, 반납한 일자 +7일
        // 연장 상태가 true 이면 연장불가
        // 1. 조회
        Boolean resultPS = lendRepository.mCheckExtendStatus(userId, request.getBookId());

        if(resultPS){
            throw new ExceptionApi400("이미 연장한 도서입니다. 더이상 연장이 불가합니다.");
        }
        // 2. 연장해주기

    }*/
}