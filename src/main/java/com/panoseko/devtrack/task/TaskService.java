package com.panoseko.devtrack.task;

import com.panoseko.devtrack.image.Image;
import com.panoseko.devtrack.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ImageService imageService;


    @Autowired
    public TaskService(TaskRepository taskRepository, ImageService imageService) {
        this.taskRepository = taskRepository;
        this.imageService = imageService;
    }

    public List<TaskResponse> getTasks(Long userId) {
        List<Task> tasks = taskRepository.findTasksByCreator(userId);
        List<TaskResponse> tasksResponse = new ArrayList<>();
        for (Task task : tasks) {
            TaskResponse taskResponse = new TaskResponse(task);
            Image image = task.getImage();
            if (image != null) {
                image.setImageData(imageService.decompressImageData(image.getImageData()));
                taskResponse.setImage(image);
            }
            tasksResponse.add(taskResponse);
        }
        return tasksResponse;
    }

//    public Map<Status, List<TaskResponse>> getTasksByStatus(Long userId) {
//        List<Task> tasks = taskRepository.findTasksByCreator(userId);
//        Map<Status, List<TaskResponse>> tasksByStatus = new HashMap<>();
//
//        for (Task task : tasks) {
//            Status status = task.getStatus();
//            TaskResponse taskResponse = new TaskResponse(task);
//            Image image = task.getImage();
//            if (image != null) {
//                image.setImageData(imageService.decompressImageData(image.getImageData()));
//                taskResponse.setImage(image);
//            }
//
//            tasksByStatus.computeIfAbsent(status, k -> new ArrayList<>()).add(taskResponse);
//        }
//
//        return tasksByStatus;
//    }


    @Transactional
    public Long addTask(AddTaskRequest addTaskRequest, Long userId) {
        Task task = new Task(
                addTaskRequest.getTitle(),
                addTaskRequest.getDescription(),
                addTaskRequest.getStatus(),
                addTaskRequest.getCreatedAt(),
                userId
        );
        if (addTaskRequest.getImage() != null) {
            try {
                System.out.println("image file is not null");
                Image uploadedImage = imageService.uploadImage(addTaskRequest.getImage(), task);
                task.setImage(uploadedImage);
            } catch (Exception e) {
                // Handle the exception
                e.printStackTrace();
                throw new IllegalStateException("Error uploading image");
            }
        }
        return taskRepository.save(task).getId();
    }

    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            imageService.deleteImage(taskId);
            taskRepository.deleteById(taskId);
        } else {
            throw new IllegalStateException("Task with id " + taskId + " does not exist");
        }
    }

    public void updateTaskStatus(Long taskId, Status status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setStatus(status);
        taskRepository.save(task);
    }

//    @Transactional
    public void updateTask(UpdateTaskRequest updateTaskRequest,  Long userId) {
        System.out.println("Update task id: " + updateTaskRequest.getId());
        Task task = new Task(
                updateTaskRequest.getId(),
                updateTaskRequest.getTitle(),
                updateTaskRequest.getDescription(),
                updateTaskRequest.getStatus(),
                updateTaskRequest.getCreatedAt(),
                userId
        );
        if (updateTaskRequest.getImage() != null) {
            try {
                System.out.println("image file is not null");
                imageService.deleteImage(task.getId());
                Image uploadedImage = imageService.uploadImage(updateTaskRequest.getImage(), task);
                task.setImage(uploadedImage);
            } catch (Exception e) {
                // Handle the exception
                e.printStackTrace();
                throw new IllegalStateException("Error uploading image");
            }
        } else {
            System.out.println("image file is null");
            imageService.deleteImage(task.getId());
        }
        taskRepository.save(task);
    }

//    public void updateTask(Task task) {
//        if (taskRepository.existsById(task.getId())) {
//            taskRepository.save(task);
//        } else {
//            throw new IllegalStateException("Task with id " + task.getId() + " does not exist");
//        }
//    }

//    public Map<Status, List<TaskResponse>> groupTasksByStatus(List<TaskResponse> tasks) {
//        Map<Status, List<TaskResponse>> tasksByStatus = new HashMap<>();
//        for (TaskResponse task : tasks) {
//            Status status = task.getStatus();
//            tasksByStatus.computeIfAbsent(status, k -> new ArrayList<>()).add(task);
//        }
//        return tasksByStatus;
//    }
}
//    public Map<Status, List<TaskResponse>> groupTasksByStatus(List<TaskResponse> tasks) {
//        Map<Status, List<TaskResponse>> tasksByStatus = new HashMap<>();
//
//        for (TaskResponse task : tasks) {
//            Status status = task.getStatus();
//            tasksByStatus.computeIfAbsent(status, k -> new ArrayList<>()).add(task);
//        }
//        return tasksByStatus;
//    }
//}
//
//    public List<Task> getTasks(Long createdById) {
//        List<Task> tasks=  taskRepository.findTaskWithImage(createdById);
//        for(Task task : tasks){
//            task.setImageData(imageService.downloadImage(task.getId()));
//        }
//        return tasks;
//    }


//    public Map<Status, List<Task>> getTasksGroupedByStatusByCreatorId(Long createdById) {
//        List<Task> tasksByCreator = getTasksByCreatorId(createdById);
//        return groupTasksByStatus(tasksByCreator);
//    }

//    public List<Task> getTasksByCreatorId(Long createdById) {
//        return taskRepository.findTasksByCreator(createdById);
//    }
