package green.mtcoding.bookbox.like;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 즐겨찾기 추가
    @PostMapping("/api/likes")
    public void likeDo(@RequestHeader("Authorization") String token){

    }

}
