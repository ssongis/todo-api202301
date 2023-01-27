package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@Commit // test 실행 후 커밋
class TodoRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;

    /*@BeforeEach
    void insertTest(){
        // todoId는 자동으로 생성됨
        TodoEntity todo1 = TodoEntity.builder().title("저녁 장보기").build();
        TodoEntity todo2 = TodoEntity.builder().title("책 읽기").build();
        TodoEntity todo3 = TodoEntity.builder().title("목욕하기").build();

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

    }*/

    @Test
    @DisplayName("할 일 목록을 조회하면 리스트의 사이즈가 3이어야 한다.")
    void findAllTest(){
        // given
        // when
        List<TodoEntity> list = todoRepository.findAll();
        // then
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("회원의 할 일을 등록해야 한다.")
    void saveTodoWithUserTest() {
        UserEntity user = userRepository.findByEmail("iamtube@naver.com");

        // given
        TodoEntity todo = TodoEntity.builder()
                .title("잠자기")
                .user(user)
                .build();
        // when
        TodoEntity savedTodo = todoRepository.save(todo);
        // then
        assertEquals(savedTodo.getUser().getId(), user.getId());
    }

    @Test
    @DisplayName("특정 회원의 할일 목록을 조회해야 한다.")
    @Transactional
    void findByUserTest() {
        //given
        String userId = "8affff8185f0e0630185f1089d250000";
        //when
        List<TodoEntity> todos = todoRepository.findByUserId(userId);

        //then
        todos.forEach(System.out::println);
        assertEquals(3, todos.size());
    }


}