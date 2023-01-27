package com.example.todo.todoapi.entity;

import com.example.todo.userapi.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

// 일정관리 프로그램
@Setter@Getter@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "todoId")
@Builder
@Entity
@Table(name = "tbl_todo")
public class TodoEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid") // pk가 랜덤 문자열로 설계됨
    private String todoId;

    @Column(nullable = false, length = 30)
    private String title; // 제목, dto로 가져옴

    private boolean done; // 일정 완료 여부

    @CreationTimestamp
    private LocalDateTime createDate; // 등록 시간

    // 회원과 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    // 연관관계 설정은 했지만 INSERT, UPDATE시에는 이 객체를 활용하지 않겠다.
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    // 할 일 추가, 수정시 사용할 외래키
    private String user_Id;
}
