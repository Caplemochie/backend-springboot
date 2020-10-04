package ru.javabegin.tasklist.backendspringboot.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.tasklist.backendspringboot.entity.Stat;

/**
 * Created by tagir on 04.10.2020.
 */
@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {


}