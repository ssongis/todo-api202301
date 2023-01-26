package com.example.todo.userapi.repository;

import com.example.todo.userapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 이메일로 회원 조회
    // select * from tbl_user where email=?
    // UserEntity에서 email 조회
    UserEntity findByEmail(String email);

    // 이메일 중복 검사
    // select count(*) from tbl_user where email=? => unique는 0 또는 1, 0이면 중복x => false
//    @Query("select count(*) from UserEntity u where email=?1")
    // UserEntity에서 email 중복을 boolean 값으로 가져와서 존재여부 확인
    boolean existsByEmail(String email);


}
