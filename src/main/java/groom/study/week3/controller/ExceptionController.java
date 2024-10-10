package groom.study.week3.controller;

import groom.study.week3.common.exception.CustomException;
import groom.study.week3.common.Constants.ExceptionClass;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @GetMapping
    public void getRuntimeException() {
        throw new RuntimeException("getRuntimeException 메소드 호출");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleException(RuntimeException e,
                                                               HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        LOGGER.error("클래스 내 handleException 호출, {}, {}", request.getRequestURI(),
            e.getMessage());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error type", status.getReasonPhrase());
        errorResponse.put("code", "400");
        errorResponse.put("message", e.getMessage());

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @GetMapping("/custom")
    public void getCustomException() throws CustomException {
        throw new CustomException(ExceptionClass.EXCEPTION_CLASS, HttpStatus.BAD_REQUEST, "getCustomException 메소드 호출");
    }
}