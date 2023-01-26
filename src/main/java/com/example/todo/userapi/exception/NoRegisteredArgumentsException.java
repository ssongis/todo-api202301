package com.example.todo.userapi.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRegisteredArgumentsException extends RuntimeException{ // 2가지 RuntimeException 구분위해

    // 기본 생성자
    // 롬복으로 처리

    // 에러 메시지를 처리하는 생성자
    // 내가 생성
    public NoRegisteredArgumentsException(String message) {
        super(message);
    }
}
