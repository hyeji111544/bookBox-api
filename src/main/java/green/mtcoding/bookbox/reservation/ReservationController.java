package green.mtcoding.bookbox.reservation;

import green.mtcoding.bookbox.core.util.JwtUtil;
import green.mtcoding.bookbox.core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 도서 예약
    @PostMapping("/api/reservation/{isbn13}")
    public ResponseEntity<?> reserveBook(
            @RequestHeader("Authorization") String token,
            @PathVariable String isbn13) {

        // JWT 토큰에서 유저 ID 추출
        String jwtToken = token.replace("Bearer ", "");
        Long userId = JwtUtil.extractUserIdFromToken(jwtToken);

        ReservationResponse.ReservationDTO reservationDTO = reservationService.도서예약(userId, isbn13);

        return ResponseEntity.ok(Resp.ok(reservationDTO, "예약이 완료되었습니다."));
    }


    // 예약 목록 조회
    @GetMapping("/api/reservation-list")
    public ResponseEntity<List<ReservationResponse.ReservationListDTO>> getReservationList(
            @RequestHeader("Authorization") String token
         ) {

        // JWT 토큰에서 유저 ID 추출
        String jwtToken = token.replace("Bearer ", "");
        Long tokenUserId = JwtUtil.extractUserIdFromToken(jwtToken);
        List<ReservationResponse.ReservationListDTO> reservations = reservationService.예약목록조회(tokenUserId);
        return ResponseEntity.ok(reservations);
    }


    // 예약 취소
    @PutMapping("/api/reservation-cncl/{isbn13}")
    public ResponseEntity<?> cancelReservation(@PathVariable String isbn13,
                                               @RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        Long tokenUserId = JwtUtil.extractUserIdFromToken(jwtToken);
        reservationService.예약취소(tokenUserId, isbn13);
        return ResponseEntity.ok(Resp.ok("예약이 취소되었습니다."));
    }
}
