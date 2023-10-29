package com.panoseko.devtrack.task;

import com.panoseko.devtrack.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {

    private final TaskService taskService;
    private final JwtService jwtService;

    @Autowired
    public TaskController(TaskService taskService, JwtService jwtService) {
        this.taskService = taskService;
        this.jwtService = jwtService;
    }


    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getTasksByCreatorId(@CookieValue(name = "access-token") String jwtToken) {
        Long userId = jwtService.extractUserId(jwtToken);
        List<TaskResponse> tasks = taskService.getTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> addTask(@CookieValue(name = "access-token") String jwtToken,
                                     @ModelAttribute AddTaskRequest addTaskRequest) {
        Long userId = jwtService.extractUserId(jwtToken);
        Long taskId = taskService.addTask(addTaskRequest, userId);
        return ResponseEntity.ok(taskId);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateTask(@CookieValue(name = "access-token") String jwtToken,
                                        @ModelAttribute UpdateTaskRequest updateTaskRequest) {
        Long userId = jwtService.extractUserId(jwtToken);
        taskService.updateTask(updateTaskRequest, userId);
        return ResponseEntity.ok("Task updated successfully");
    }


    @PutMapping("/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Long taskId,
                                              @RequestParam("status") Status status) {
        taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok("Task status updated successfully");
    }
}