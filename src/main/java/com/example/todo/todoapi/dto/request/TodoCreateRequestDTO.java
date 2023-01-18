package com.example.todo.todoapi.dto.request;

import com.example.todo.todoapi.entity.TodoEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter@Getter@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class TodoCreateRequestDTO {
    // 등록을 위한 DTO
    @NotBlank
    @Size(min = 2, max = 10)
    private String title;

    // DTO를 Entity로 변환
    public TodoEntity toEntity(){
        return TodoEntity.builder()
                .title(this.title)
                .build();
    }
}
