package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.Task;
import ru.javabegin.tasklist.backendspringboot.repo.TaskRepository;
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



}
