package ru.javabegin.tasklist.backendspringboot.controller;

import org.springframework.web.bind.annotation.*;
import ru.javabegin.tasklist.backendspringboot.entity.Category;
import ru.javabegin.tasklist.backendspringboot.repo.CategoryRepository;

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

    @PostMapping("/add")
    public Category add(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

}
