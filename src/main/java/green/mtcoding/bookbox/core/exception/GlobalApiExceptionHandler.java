package green.mtcoding.bookbox.core.exception;


import green.mtcoding.bookbox.core.exception.api.*;
import green.mtcoding.bookbox.core.util.Msg;
import green.mtcoding.bookbox.core.util.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GlobalApiExceptionHandler {
    

    @ExceptionHandler(ExceptionApi400.class)
    public ResponseEntity<?> ex400(Exception e) {
        return new ResponseEntity<>(Msg.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 인증 실패 (클라이언트가 인증없이 요청했거나, 인증을 하거나 실패했거나)
    @ExceptionHandler(ExceptionApi401.class)
    public ResponseEntity<?> ex401(Exception e) {
        return new ResponseEntity<>(Resp.fail(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExceptionApi403.class)
    public ResponseEntity<?> ex403(Exception e) {
        return new ResponseEntity<>(Msg.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionApi404.class)
    public ResponseEntity<?> ex404(Exception e) {
        return new ResponseEntity<>(Msg.fail(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExceptionApi500.class)
    public ResponseEntity<?> ex500(Exception e) {
        return new ResponseEntity<>(Msg.fail(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ex(Exception e) {
         return new ResponseEntity<>(Msg.fail(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(Msg.fail("존재하지 않는 API 입니다."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalStateException.class})
    public ResponseEntity<?> methodArgumentTypeMismatchException(Exception e) {
        return new ResponseEntity<>(Msg.fail("잘못된 요청입니다."), HttpStatus.BAD_REQUEST);
    }





}







