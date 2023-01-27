package com.example.todo.userapi.service;

import com.example.todo.security.TokenProvider;
import com.example.todo.userapi.dto.LoginResponseDTO;
import com.example.todo.userapi.dto.UserSignUpDTO;
import com.example.todo.userapi.dto.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.exception.DuplicatedEmailException;
import com.example.todo.userapi.exception.NoRegisteredArgumentsException;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    // UserRepository에 의존
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

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

    // 이메일 중복확인
    public boolean isDuplicate(String email){
        if(email==null){
            throw new RuntimeException("이메일 값이 없습니다.");
        }
        return  userRepository.existsByEmail(email);
    }

    // 로그인 검증
    public LoginResponseDTO getByCredentials(
            final String email,
            final String rawPassword) {

        // 입력한 이메일을 통해 회원정보 조회
        UserEntity originalUser = userRepository.findByEmail(email);

        if (originalUser == null) {
            throw new RuntimeException("가입된 회원이 아닙니다.");
        }
        // 패스워드 검증 (입력 비번, DB에 저장된 비번)
        if (!passwordEncoder.matches(rawPassword, originalUser.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        log.info("{}님 로그인 성공!", originalUser.getUserName());

        // 로그인 성공 후 토큰 발급
        String token = tokenProvider.createToken(originalUser);

        return new LoginResponseDTO(originalUser, token);
    }

}
