package com.panoseko.devtrack.task;

import com.panoseko.devtrack.exception.ImageNotFoundException;
import com.panoseko.devtrack.exception.ImageProcessingException;
import com.panoseko.devtrack.exception.TaskNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/resources/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(Principal connectedUser)
            throws ImageProcessingException {
        List<TaskDTO> tasks = taskService.getTasks(connectedUser);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Long> addTask(@RequestBody @Valid AddTaskRequestDTO addTaskRequest,
                                     Principal connectedUser) throws ImageNotFoundException {
        Long taskId = taskService.addTask(addTaskRequest, connectedUser);
        return new ResponseEntity<>(taskId,  HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) throws TaskNotFoundException {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();

    }

    @PutMapping()
    public ResponseEntity<String> updateTask(@RequestBody @Valid UpdateTaskRequestDTO updateTaskRequest)
            throws TaskNotFoundException, ImageNotFoundException {
        taskService.updateTask(updateTaskRequest);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long taskId,
                                                   @RequestParam("status") Status status)
            throws TaskNotFoundException{
        taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok().build();
    }
}

