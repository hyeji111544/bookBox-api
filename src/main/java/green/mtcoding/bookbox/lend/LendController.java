package green.mtcoding.bookbox.lend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LendController {
    private final LendService lendService;
}
