package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.tasklist.backendspringboot.entity.Priority;
import ru.javabegin.tasklist.backendspringboot.entity.Stat;
import ru.javabegin.tasklist.backendspringboot.repo.PriorityRepository;
import ru.javabegin.tasklist.backendspringboot.repo.StatRepository;
import ru.javabegin.tasklist.backendspringboot.util.MyLogger;

import java.util.List;

/**
 * Created by tagir on 04.10.2020.
 */

@RestController
public class StatController {

    public StatController(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    private final StatRepository statRepository;  //сервис для доступа к данным (напрямую к репозиториям)

    private final Long defaultId = 1L; // L - чтобы тип числа был Long, инчае будет ошибка компиляции

    // для статистики получаем всегда только одну строку с id = 1 (согласно таблице с БД)
    @GetMapping("/stat")
    public ResponseEntity<Stat> findById() {

        MyLogger.showMethodName("StatController: findById() --------------------------------------------------------------");

        // можно не использовать ResponseEntity, а просто вернуть коллекцию, код все равно будет 200 ОК
        return ResponseEntity.ok(statRepository.findById(defaultId).get());
    }

    public static void showMethodName(String name) {
        System.out.println();
        System.out.println();
        System.out.println(name);
    }
}




