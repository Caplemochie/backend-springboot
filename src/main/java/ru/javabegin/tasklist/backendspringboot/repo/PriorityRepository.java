package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.Category;
import ru.javabegin.tasklist.backendspringboot.entity.Priority;

import java.util.List;

/**
 * Created by tagir on 30.09.2020.
 */

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {


    // если title = null или =='', то получим всезначения
    @Query("SELECT c FROM Priority c where " +
            "(:title is null or :title='' lower(c.title) like lower(concat('%', :title,'%')))   " +
            "order by c.title asc")
    List<Priority> findByTitle(@Param("title") String title);


    // получить все значения, сортировка по id
    List<Priority> findAllByOrderByIdAsc();

}
