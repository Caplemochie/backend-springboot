package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.Category;

import java.util.List;

/**
 * Created by tagir on 30.09.2020.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // получить все значения, сортировка по наименованию
    List<Category> findAllByOrderByTitleAsc();


}
