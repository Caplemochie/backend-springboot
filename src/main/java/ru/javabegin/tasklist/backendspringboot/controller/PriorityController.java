package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.tasklist.backendspringboot.entity.Priority;
import ru.javabegin.tasklist.backendspringboot.repo.PriorityRepository;

import java.util.List;

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

    @GetMapping("/test")
    public List<Priority> test() {

        List<Priority> list = priorityRepository.findAll();
        System.out.println("list = " + list);

        return list;
    }

}
