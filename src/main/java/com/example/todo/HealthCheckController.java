package com.example.todo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {
    // 서버가 잘 돌아가는지 확인
    @GetMapping("/")
    public ResponseEntity<?> check(){
        log.info("server is running...");
        return ResponseEntity
                .ok()
                .body("server is running...");
    }

}
