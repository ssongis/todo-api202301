package com.example.todo.userapi.service;

import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.exception.DuplicatedEmailException;
import com.example.todo.userapi.exception.NoRegisteredArgumentsException;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    // UserRepository에 의존
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    // 회원가입 처리
    public UserSignUpResponseDTO create(final UserSignUpDTO userSignUpDTO){ //UserSignUpResponseDTO로 리턴
        if (userSignUpDTO == null){
            throw new NoRegisteredArgumentsException("가입정보가 없습니다.");
        }
        final String email = userSignUpDTO.getEmail(); // 회원가입한 email 정보 조회
        if(userRepository.existsByEmail(email)){ // email 값이 1이면 중복 (existByEmail은 boolean 타입)
            log.warn("Email already exists - {}", email);
            throw new DuplicatedEmailException("중복된 이메일입니다.");
        }
        // 패스워드 인코딩
        String rawpassword = userSignUpDTO.getPassword(); // 평문 암호
        String encodedPassword = passwordEncoder.encode(rawpassword); // 암호화 처리
        userSignUpDTO.setPassword(encodedPassword); // 암호화된 패스워드 세팅

        // 엔터티로 변환한 값 저장
        UserEntity savedUser = userRepository.save(userSignUpDTO.toEntity());
        log.info("회원 가입 성공!! - user_id : {}",savedUser.getId());
        // save의 리턴 결과 저장
        return new UserSignUpResponseDTO(savedUser);

    }
}
