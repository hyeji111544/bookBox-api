package green.mtcoding.bookbox.like;

import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 즐겨찾기 추가
    @PostMapping("/api/likes")
    public ResponseEntity<?> likeDo(@RequestHeader("Authorization") String token, @RequestBody LikeRequest.SaveDTO request) {
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        LikeResponse.SaveDTO result = likeService.즐겨찾기추가(userId, request);

        return ResponseEntity.ok(Resp.ok(result, "즐겨찾기에 추가되었습니다."));
    }

    // 즐겨찾기 조회
    @GetMapping("/api/likes")
    public ResponseEntity<?> likeList(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        LikeResponse.ListDTO result = likeService.즐겨찾기목록조회(userId);

        return ResponseEntity.ok(Resp.ok(result));


    }

    // 즐겨찾기 삭제
    @DeleteMapping("/api/likes")
    public ResponseEntity<?> unlikeDo(@RequestHeader("Authorization") String token, @RequestBody LikeRequest.DeleteDTO request) {
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        likeService.즐겨찾기삭제(userId, request);

        return ResponseEntity.ok(Resp.ok(null, "삭제 성공"));

    }


}
