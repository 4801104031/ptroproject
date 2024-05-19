package vn.hienld.admin.error;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.hienld.admin.dto.BaseResponse;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleUserServiceException(BadRequestException ex) {
        BaseResponse errorMessage = new BaseResponse("500", ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex) {
        BaseResponse errorMessage = new BaseResponse("500", ex.toString());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        BaseResponse errorMessage = new BaseResponse("501", ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {InsufficientAuthenticationException.class})
    public ResponseEntity<Object> handleAuthenException(InsufficientAuthenticationException ex) {
        BaseResponse errorMessage = new BaseResponse("403", ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}
