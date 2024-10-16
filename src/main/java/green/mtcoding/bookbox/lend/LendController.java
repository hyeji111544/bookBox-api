package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LendController {

    private final LendService lendService;

    // 대여하기
    @PostMapping("/api/lends")
    public ResponseEntity<?> lendDo(@RequestHeader("Authorization") String token, @RequestBody LendRequest.SaveDTO request){
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        LendResponse.LendDTO result = lendService.대여하기(userId, request);

        return ResponseEntity.ok(Resp.ok(result, "대여되었습니다. 내서재에서 대여된 도서를 확인하세요."));
    }

    // 반납하기
    // TODO: 다음 예약자에게 자동으로 넘어가도록 처리 - 민재씨가 추가완료함
    @PutMapping("/api/lends/return")
    public ResponseEntity<?>  lendReturn(@RequestHeader("Authorization") String token, @RequestBody LendRequest.ReturnDTO request){
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        LendResponse.ReturnDTO result = lendService.직접반납하기(userId, request);

        return ResponseEntity.ok(Resp.ok(result, "성공적으로 반납되었습니다."));
    }


    // TODO: 테스트용 API - 신민재
    @PutMapping("/api/lends/auto-return")
    public ResponseEntity<?> triggerAutoReturn() {
        lendService.자동반납(); // LendService의 자동반납 메소드 호출
        return ResponseEntity.ok("대여기간이 만료되어 자동으로 반납처리 되었습니다.");
    }


    //TODO: 테스트용 컨트롤러 (지우기)
    // 자동반납 테스트
/*    @PutMapping("/lends/auto-return")
    public ResponseEntity<?> lendAutoReturn(){

        LendResponse.AutoReturnDTO result = lendService.자동반납();

        return ResponseEntity.ok(Resp.ok(result));
    }*/

    // 현재 대여중인 도서 목록
    @GetMapping("/api/lends/list")
    public ResponseEntity<?> lendList(@RequestHeader("Authorization") String token){ //request객체로 받는걸로 바꾸기
        // Bearer 제거
        String jwtToken = token.replace("Bearer ", "");
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        LendResponse.ListDTO listDTO = lendService.대여중인도서목록조회(userId);

        return ResponseEntity.ok(Resp.ok(listDTO));
    }


    // 대여중인 도서 연장
    @PutMapping("/api/lends/extension")
    public ResponseEntity<?> lendExtension(@RequestHeader("Authorization") String token, @RequestBody LendRequest.ExtendDTO request){
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        LendResponse.ExtensionDTO result = lendService.대여중인도서연장(userId,request);

        return ResponseEntity.ok(Resp.ok(result));
    }


    // 지금까지 대여했던 도서들 리스트
    @GetMapping("/api/lends/list/history")
    public ResponseEntity<?> lendListSoFar(@RequestHeader("Authorization") String token){

        // 중복 제거 필요
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        List<LendResponse.HistoryDTO> result = lendService.지금까지대여한도서들목록(userId);

        return ResponseEntity.ok(Resp.ok(result));


    }
}
