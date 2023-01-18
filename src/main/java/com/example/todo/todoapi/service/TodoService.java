package com.example.todo.todoapi.service;

import com.example.todo.todoapi.dto.request.TodoCreateRequestDTO;
import com.example.todo.todoapi.dto.request.TodoModifyRequestDTO;
import com.example.todo.todoapi.dto.response.TodoDetailResponseDTO;
import com.example.todo.todoapi.dto.response.TodoListResponseDTO;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor //Autowired 사용할 필요x
public class TodoService {

    private final TodoRepository todoRepository;

    // 할 일 목록 조회

    public TodoListResponseDTO retrieve() {
        List<TodoEntity> entityList = todoRepository.findAll();
        // Entity를 DTO로 변환
        List<TodoDetailResponseDTO> dtoList = entityList.stream()
                .map(TodoDetailResponseDTO::new)
                .collect(Collectors.toList());

        return TodoListResponseDTO.builder()
                .todos(dtoList)
                .build();
    }

    // 할 일 등록
    // 클라이언트에서 받은 내용 전달
    public TodoListResponseDTO create(final TodoCreateRequestDTO createRequestDTO){

        todoRepository.save(createRequestDTO.toEntity());
        log.info("할 일이 저장되었습니다.",createRequestDTO.getTitle());
        return retrieve();
    }

    // 할 일 수정 (제목, 할일 완료여부)
    public TodoListResponseDTO update(
            final String id,
            final TodoModifyRequestDTO modifyRequestDTO // 실질적으로 수정할 데이터
    ) {
        Optional<TodoEntity> targetEntity = todoRepository.findById(id);

        targetEntity.ifPresent(entity -> {
            entity.setTitle(modifyRequestDTO.getTitle());
            entity.setDone(modifyRequestDTO.isDone());

            todoRepository.save(entity);
        });

        return retrieve();
    }

    // 할 일 삭제

    public TodoListResponseDTO delete(final String id){

        try{
            todoRepository.deleteById(id);
        }catch (Exception e){
            log.error("id가 존재하지 않아 삭제에 실패했습니다 - ID: {}, err: {}", id,e.getMessage());
            throw new RuntimeException("id가 존재하지 않아 삭제에 실패했습니다."); // 클라이언트가 보는 메시지
        }
        return retrieve();
    }
}