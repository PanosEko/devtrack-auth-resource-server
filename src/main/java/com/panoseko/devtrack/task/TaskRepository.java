package com.panoseko.devtrack.task;

import com.panoseko.devtrack.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);

//    @Query("SELECT t FROM Task t WHERE t.user_id = ?1")
//    List<Task> findTasksByUser(Long createdById);
}
