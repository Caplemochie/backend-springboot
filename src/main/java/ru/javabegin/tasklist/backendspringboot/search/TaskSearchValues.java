package ru.javabegin.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by tagir on 06.10.2020.
 */

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

// возможные значения, по которым можно искать категории
public class TaskSearchValues {


    // поля поиска (все типы - объектные, не примитивные, чтобы можно было передать null)
    private String title;
    private Integer completedId;
    private Long priorityId;
    private Long categoryId;


}
