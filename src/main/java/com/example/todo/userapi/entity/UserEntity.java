package com.example.todo.userapi.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //모든 값들을 비교하지만 id만 비교해도 괜찮음
@Builder
@Entity
@Table(name = "tbl_user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "system-uuid") // 유일한 ID값을 얻기 위해
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id; // 계정명이 아니라 식별코드

    @Column(unique = true, nullable = false) // db쪽에서 검사
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;
    @CreationTimestamp
    private LocalDateTime joinDate;
}





