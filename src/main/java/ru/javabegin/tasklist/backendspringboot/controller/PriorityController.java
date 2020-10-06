package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.Priority;
import ru.javabegin.tasklist.backendspringboot.repo.PriorityRepository;
import ru.javabegin.tasklist.backendspringboot.search.PrioritySearchValues;
import ru.javabegin.tasklist.backendspringboot.util.MyLogger;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by tagir on 30.09.2020.
 */
@RestController
@RequestMapping("/priority")
public class PriorityController {

    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    private PriorityRepository priorityRepository;

    @GetMapping("/all")
    public List<Priority> findAll() {
        MyLogger.showMethodName("PriorityController: findAll() --------------------------------------------------------------");

        return priorityRepository.findAllByOrderByIdAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {

        MyLogger.showMethodName("PriorityController: add() --------------------------------------------------------------");


        // проверка на обязательные параметры
        if (priority.getId() != null && priority.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("returned param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение color
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(priorityRepository.save(priority)); // save работает как на добавление, так и на обновление
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody Priority priority) {

        MyLogger.showMethodName("PriorityController: update() --------------------------------------------------------------");

        // проверка на обязательные параметры
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение color
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }



        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {

        MyLogger.showMethodName("PriorityController: findById() --------------------------------------------------------------");

        Priority priority = null;

        try{
            priority = priorityRepository.findById(id).get();
        }catch (NoSuchElementException e) {   // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id=" +id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(priorityRepository.findById(id).get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        MyLogger.showMethodName("PriorityController: delete() --------------------------------------------------------------");

        try{
            priorityRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {   // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id=" +id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // не возвращаем удаленный объект
    }

    // поиск по любым параметрам PrioritySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {

        MyLogger.showMethodName("PriorityController: search() --------------------------------------------------------------");

        // если вместо текста будет пусто или null - вернутся все категории
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getText()));
    }



}
