package com.panoseko.devtrack.task;

import com.panoseko.devtrack.exception.ImageNotFoundException;
import com.panoseko.devtrack.exception.ImageProcessingException;
import com.panoseko.devtrack.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/resources/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(Principal connectedUser)
            throws ImageProcessingException {
        List<TaskDTO> tasks = taskService.getTasks(connectedUser);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Long> addTask(@RequestBody AddTaskRequestDTO addTaskRequest,
                                     Principal connectedUser) throws ImageNotFoundException {
        Long taskId = taskService.addTask(addTaskRequest, connectedUser);
        return new ResponseEntity<>(taskId,  HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) throws TaskNotFoundException {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @PutMapping()
    public ResponseEntity<String> updateTask(@RequestBody UpdateTaskRequestDTO updateTaskRequest)
            throws TaskNotFoundException, ImageNotFoundException {
        taskService.updateTask(updateTaskRequest);
        return ResponseEntity.ok("Task updated successfully");
    }

// TODO validate @NotNull Status status
    @PutMapping("/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long taskId, @RequestParam("status") Status status)
            throws TaskNotFoundException{
        taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok("Task updated successfully");
    }
}

//    @RequestMapping(method = RequestMethod.PUT, consumes = {"multipart/form-data"})
//    public ResponseEntity<?> updateTask(@CookieValue(name = "access-token") String jwtToken,
//                                        @ModelAttribute UpdateTaskRequestDTO updateTaskRequest) {
//        Long userId = jwtService.extractUserId(jwtToken);
//        taskService.updateTask(updateTaskRequest, userId);
//        return ResponseEntity.ok("Task updated successfully");
//    }


//    @GetMapping()
//    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@CookieValue(name = "access-token") String jwtToken) {
//        Long userId = jwtService.extractUserId(jwtToken);
//        List<TaskDTO> tasks = taskService.getTasks(userId);
//        return ResponseEntity.ok(tasks);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<?> addTask(@CookieValue(name = "access-token") String jwtToken,
//                                     @RequestBody AddTaskRequestDTO addTaskRequest,
//                                     Principal connectedUser) {
//        Long userId = jwtService.extractUserId(jwtToken);
//        Long taskId = taskService.addTask(addTaskRequest, connectedUser);
//        return ResponseEntity.ok(taskId);
//    }
