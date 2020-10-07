package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.Task;

/**
 * Created by tagir on 30.09.2020.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


}
