package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.tasklist.backendspringboot.entity.Category;
import ru.javabegin.tasklist.backendspringboot.entity.Priority;
import ru.javabegin.tasklist.backendspringboot.repo.CategoryRepository;
import ru.javabegin.tasklist.backendspringboot.repo.PriorityRepository;

import java.util.List;

/**
 * Created by tagir on 30.09.2020.
 */

@RestController
@RequestMapping("/category")
public class CategoryController {


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryRepository categoryRepository;

    @GetMapping("/test")
    public List<Category> test() {

        List<Category> list = categoryRepository.findAll();
        System.out.println("list = " + list);

        return list;
    }

}
