package green.mtcoding.bookbox.core.filter;

import green.mtcoding.bookbox.core.util.Msg;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.regex.Pattern;

public class UrlFilter implements Filter {

    private static final Pattern INVALID_URL_PATTERN = Pattern.compile("[^\\w?&=:/]|(?<!:)/{2,}");


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        String fullURL = requestURL.toString() + (queryString == null ? "" : "?" + queryString );


        String h2Url = "http://localhost:8080/h2-console";

        if(INVALID_URL_PATTERN.matcher(fullURL).find() && !INVALID_URL_PATTERN.matcher(h2Url).find()) {
            noticeWrongUrl(response);
            return;
        }
        filterChain.doFilter(request, response);
    }



    private static void noticeWrongUrl(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json; charset=utf-8"); // 바디데이터의 인코딩을 설명하는 것
        //response.setCharacterEncoding("utf-8"); // 이거는 데이터를 인코딩하는 것. (기본값이 utf-8) 컨텐트 타입에 붙은 charset과 구별 가능해야됨.
        response.getWriter().write(Msg.fail("잘못된 주소입니다."));
    }
}
