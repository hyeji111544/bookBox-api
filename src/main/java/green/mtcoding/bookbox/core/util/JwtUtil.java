package green.mtcoding.bookbox.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import green.mtcoding.bookbox.admin.Admin;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.user.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "bookbox";  // 비밀 키 설정
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private static final long TEST_TIME = 1000 * 60 * 5;  // 5분



    // =================== User ============================
    // JWT 토큰 생성
    public static String createUserToken(User user) {
        String accessToken = JWT.create()
                .withSubject("auth") // 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + TEST_TIME)) //만료시간 7일 -> 테스트겸 5분으로 해둠
                .withClaim("id", user.getId()) // payload에 추가. 개인정보 넣지 않고 검증을 위한 id정도만(인조키 id번호)
                .withClaim("role", "USER") // 역할: 사용자
                .sign(Algorithm.HMAC512(SECRET_KEY)); //우리가 암호화 하고 우리가 복호화 해서 확인할 것이므로 대칭키 사용
        return accessToken;
    }

    // 검증 코드
    public static User verify(String jwt){
        //JWT 토큰을 검증할 때는 Bearer을 떼야 한다.
        jwt = jwt.replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(jwt);

        // TODO: 신민재 추가
        // 역할이 USER 인지 확인
        String role = decodedJWT.getClaim("role").asString();
        if (!"USER".equals(role)) {
            throw new ExceptionApi400("유효하지 않은 JWT 토큰입니다.");
        }

        Long id = decodedJWT.getClaim("id").asLong(); // ID 추출

        return User.builder()
                .id(id)
                .build();
    }

    //TODO : 토큰 가지고 오기
    public static String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 'Bearer ' 이후의 토큰 부분만 추출
        }
        return null;
    }




    // =============================================



    // =================== Admin ============================
    // JWT 토큰 생성
    public static String createAdminToken(Admin admin) {
        return JWT.create()
                .withSubject("admin")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", admin.getId())
                .withClaim("role", "ADMIN")
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    // JWT 토큰 검증
    public static Admin verifyAdminToken(String jwt){
        jwt = jwt.replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(jwt);

        // 역할이 Admin인지 확인
        String role = decodedJWT.getClaim("role").asString();
        if (!"ADMIN".equals(role)) {
            throw new ExceptionApi400("유효하지 않은 JWT 토큰입니다.");
        }

        Long id = decodedJWT.getClaim("id").asLong();

        return Admin.builder()
                .id(id)
                .build();
    }

    // 토큰에서 USERID를 추출하는 메서드
    public static Long extractUserIdFromToken(String token) {
        // 토큰에서 Bearer 제거 (필요한 경우)
        token = token.replace("Bearer ", "");

        // JWT 파싱 및 검증
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token);

        // 'id' 클레임에서 값 추출
        return decodedJWT.getClaim("id").asLong();
    }




}
