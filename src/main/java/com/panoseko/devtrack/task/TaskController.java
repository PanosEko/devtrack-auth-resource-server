package com.panoseko.devtrack.task;

import com.panoseko.devtrack.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Map;

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

    @RequestMapping(method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> addTask(@CookieValue(name = "access-token") String jwtToken,
                                         @ModelAttribute AddTaskRequest addTaskRequest) {
            Long userId = jwtService.extractUserId(jwtToken);
            Long taskId = taskService.addTask(addTaskRequest,userId);
            return ResponseEntity.ok(taskId);
        }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = { "multipart/form-data" })
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

//    @PutMapping
//    public ResponseEntity<?> updateTask(@CookieValue(name = "token") String jwtToken, @RequestBody Task task) {
//        Long userId = jwtService.extractUserId(jwtToken);
//        task.setCreatedById(userId);
//        taskService.updateTask(task);
//        return ResponseEntity.ok("Task updated successfully");
//    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping()
//    public void redirectToLogin(HttpServletResponse response) throws IOException {
//        response.sendRedirect("http://localhost:3000/login");
//    }


//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<?> redirect() {
//        System.out.println("Redirecting");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", "http://localhost:3000/login"); // Set the redirect URL
//        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

//        return ResponseEntity.status(HttpStatus.FOUND).location(
//                URI.create("http://localhost:3000/login")).build();

//        return ResponseEntity.status(HttpStatus.SEE_OTHER)
//                .header("Location", "http://localhost:3000/login")
//                .build();
//    }

}
//    @GetMapping()
//    public ResponseEntity<Map<Status, List<Task>>> getTasksGroupedByStatus(
//            @CookieValue(name = "token") String jwtToken) {
//        if (jwtToken == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        } else {
//            Long createdById = jwtService.extractUserId(jwtToken);
//            Map<Status, List<Task>> tasksByStatus = taskService.getTasksGroupedByStatusByCreatorId(createdById);
//            return ResponseEntity.ok(tasksByStatus);
//        }
//    }

//    @GetMapping()
//    public ResponseEntity<List<Task>> getTasksByCreatorId(@CookieValue(name = "token") String jwtToken) {
//        if (jwtToken == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        } else {
//            Long userId = jwtService.extractUserId(jwtToken);
//            List<Task> tasks = taskService.getTasksByCreatorId(userId);
//            return ResponseEntity.ok(tasks);
//        }
//    }


//
//    @PostMapping
//    public ResponseEntity<?> addNewTask(@CookieValue(name = "token") String jwtToken,
//                                        @RequestParam("title") String title,
//                                        @RequestParam("description") String description,
//                                        @RequestParam("status") Status status,
//                                        @RequestParam("createdAt") LocalDate createdAt,
//                                        @RequestParam("image") MultipartFile image) {
//        Long userId = jwtService.extractUserId(jwtToken);
//        Task task = new Task(title, description, status, createdAt, userId, null);
//        if (jwtToken == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);}
//
//        task = taskService.addNewTask(task);
//        if(!image.isEmpty()) {
//            try {
//                Image uploadedImage = imageService.uploadImage(image, task);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("Error uploading image");
//            }
//        }
//            return ResponseEntity.ok(task.getId());
//        }