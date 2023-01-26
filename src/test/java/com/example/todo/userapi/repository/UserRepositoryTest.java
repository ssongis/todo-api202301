package com.example.todo.userapi.repository;

import com.example.todo.userapi.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("회원가입에 성공해야 한다.")
    @Transactional
    @Rollback
    void saveTest() {
        // given
        // 회원가입할 때 email, password, userName 빌드
        UserEntity user = UserEntity.builder()
                .email("abc1234@def.com")
                .password("1234")
                .userName("말똥이")
                .build();
        //when
        // 회원가입 정보 savedUser에 저장
        UserEntity savedUser = userRepository.save(user);
        //then
        // savedUser가 제대로 생성(회원가입)되었는지 확인
        assertNotNull(savedUser);
    }

    @Test
    @DisplayName("이메일로 회원을 조회해야 한다.")
    void findByEmailTest(){
        // given
        // 위에서 등록한 이메일 확인하기 위함
        String email = "abc1234@def.com";
        //when
        // userRepository.save로 저장한 email을 찾아옴
        UserEntity foundUser = userRepository.findByEmail(email);
        //then
        //
        assertEquals("말똥이", foundUser.getUserName());
    }

    @Test
    @DisplayName("이메일 중복을 체크해야 한다.")
    void existEmailTest(){
        //given
        String email = "sjflsjd@def.com";
        //when
        boolean flag = userRepository.existsByEmail(email);
        //then
        assertFalse(flag);
    }
}