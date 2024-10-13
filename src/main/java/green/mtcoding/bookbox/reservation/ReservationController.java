package green.mtcoding.bookbox.reservation;

import green.mtcoding.bookbox.core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/api/books/reservation/{isbn13}")
    public ResponseEntity<?> reserveBook(@PathVariable String isbn13, @RequestParam Long userId) {
        reservationService.도서예약(userId, isbn13);
        return ResponseEntity.ok(Resp.ok("예약이 완료되었습니다"));
    }


    // 예약 목록 조회
    @GetMapping("/api/books/reservation-list")
    public ResponseEntity<List<ReservationResponse.ReservationDTO>> getReservationList(@RequestParam Long userId) {
        List<ReservationResponse.ReservationDTO> reservations = reservationService.예약목록조회(userId);
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/api/books/reservation-cncl/{isbn13}")
    public ResponseEntity<?> cancelReservation(@PathVariable String isbn13, @RequestParam Long userId) {
        reservationService.예약취소(userId, isbn13);
        return ResponseEntity.ok(Resp.ok("에약이 취소되었습니다."));
    }
}
