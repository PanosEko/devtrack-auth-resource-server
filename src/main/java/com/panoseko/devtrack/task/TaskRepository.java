package com.panoseko.devtrack.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.createdById = ?1")
    List<Task> findTasksByCreator(Long createdById);

//    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.image WHERE t.createdById = ?1")
//    List<Task> findTaskWithImage(Long createdById);
}
