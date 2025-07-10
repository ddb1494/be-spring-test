package com.example.be_spring_test.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (status != null) {
            try {
                httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));
            } catch (Exception ex) {
                // 상태 코드를 파싱할 수 없는 경우 기본값 유지
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", httpStatus.value());

        if (httpStatus == HttpStatus.NOT_FOUND) {
            body.put("error", "Not Found");
            body.put("message", "The requested route does not exist.");
        } else {
            body.put("error", httpStatus.getReasonPhrase());
            body.put("message", "An unexpected error occurred.");
        }

        return new ResponseEntity<>(body, httpStatus);
    }
}
