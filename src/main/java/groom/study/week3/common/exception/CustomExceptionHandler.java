package groom.study.week3.common.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //(basePackages = "groom.study.week3.common.exception")
public class CustomExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> handleException(CustomException e,
                                                               jakarta.servlet.http.HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        LOGGER.error("Advice 내 exceptionHandler 호출, {}, {}", request.getRequestURI(), e.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());
        errorResponse.put("error type", e.getHttpStatusType());
        errorResponse.put("code", String.valueOf(e.getHttpStatusCode()));
        return new ResponseEntity<>(errorResponse, headers, e.getHttpStatus());

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleException(RuntimeException e,
                                                               jakarta.servlet.http.HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        LOGGER.error("Advice 내 exceptionHandler 호출, {}, {}", request.getRequestURI(), e.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error type", status.getReasonPhrase());
        errorResponse.put("message", e.getMessage());
        errorResponse.put("code", "400");

        return new ResponseEntity<>(errorResponse, headers, status);

    }
}