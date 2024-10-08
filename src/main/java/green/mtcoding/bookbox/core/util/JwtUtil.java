package green.mtcoding.bookbox.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import green.mtcoding.bookbox.user.User;

import java.util.Date;

public class JwtUtil {
    public static String create(User user) {
        String accessToken = JWT.create()
                .withSubject("auth") // 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) //만료시간 7일
                .withClaim("id", user.getId()) // payload에 추가. 개인정보 넣지 않고 검증을 위한 id정도만(인조키 id번호)
                .sign(Algorithm.HMAC512("bookbox")); //우리가 암호화 하고 우리가 복호화 해서 확인할 것이므로 대칭키 사용
        return accessToken;
    }

    //검증 코드
    public static User verify(String jwt){
        //JWT 토큰을 검증할 때는 Bearer을 떼야 한다.
        jwt = jwt.replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("bookbox")).build().verify(jwt);
        Long id = decodedJWT.getClaim("id").asLong();

        return User.builder()
                .id(id)
                .build();
    }

}
