package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.Task;
import ru.javabegin.tasklist.backendspringboot.repo.TaskRepository;
import ru.javabegin.tasklist.backendspringboot.search.TaskSearchValues;
import ru.javabegin.tasklist.backendspringboot.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by tagir on 30.09.2020.
 */

@RestController
@RequestMapping("/task")
public class TaskController {


    public TaskController(TaskRepository taskRepository, ConfigurableEnvironment configurableEnvironment) {
        this.taskRepository = taskRepository;
    }

    private TaskRepository taskRepository;

    @GetMapping("/all")
    public List<Task> findAll() {

        MyLogger.showMethodName("TaskController: findAll() --------------------------------------------------------------");
        return taskRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {

        MyLogger.showMethodName("TaskController: add() ------------------------------------------------------------------");

        // проверка на обязательные параметры
        if (task.getId() != null && task.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("returned param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {


        MyLogger.showMethodName("TaskController: update() ------------------------------------------------------------------");

        // проверка на обязательные параметры
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }




        // save работает как на добавление, так и на обновление
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {


        MyLogger.showMethodName("TaskController: findById() -------------------------------------------------------------");

        Task task = null;

        try{
            task = taskRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" +id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(taskRepository.findById(id).get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {


        MyLogger.showMethodName("TaskController: delete() ---------------------------------------------------------------");

        try{
          taskRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {   // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id=" +id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // не возвращаем удаленный объект
    }
    // поиск по любым параметрам
    // TaskSearchValues содержит все возможные параметры поиска

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {

        MyLogger.showMethodName("task: search() ---------------------------------------------------------------- ");


        // имитация загрузки (для тестирования индикаторов загрузки)
//        imitateLoading();

        // исключить NullPointerException
        String text = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;

        // конвертируем Boolean в Integer
        Integer completed = taskSearchValues.getCompletedId() != null ?  taskSearchValues.getCompletedId() : null;

        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;


        // объект сортировки
        Sort.Direction direction = sortDirection == null || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortColumn);


        // объект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        // результат запроса с постраничным выводом
        Page result = taskRepository.findByParams(text, completed, priorityId, categoryId, pageRequest);
        
        // результат запроса
        return ResponseEntity.ok(result);

    }


}
