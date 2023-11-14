package com.panoseko.devtrack.task;

import com.panoseko.devtrack.image.Image;
import com.panoseko.devtrack.image.ImagePreview;
import com.panoseko.devtrack.image.ImageRepository;
import com.panoseko.devtrack.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository, ImageService imageService, ImageRepository imageRepository) {
        this.taskRepository = taskRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    public List<TaskResponse> getTasks(Long userId) {
        List<Task> tasks = taskRepository.findTasksByCreator(userId);
        List<TaskResponse> tasksResponses = new ArrayList<>();
        for (Task task : tasks) {
            TaskResponse taskResponse = new TaskResponse(task);
            Image image = task.getImage();
            if (image != null) {
                ImagePreview imagePreview = new ImagePreview(image.getId(),
                        imageService.decompressImageData(image.getImagePreview()));
                taskResponse.setImagePreview(imagePreview);
            }
            tasksResponses.add(taskResponse);
        }
        return tasksResponses;
    }

    @Transactional
    public Long addTask(AddTaskRequest addTaskRequest, Long userId) {
        Task task = new Task(
                addTaskRequest.getTitle(),
                addTaskRequest.getDescription(),
                addTaskRequest.getStatus(),
                addTaskRequest.getCreatedAt(),
                userId
        );
        if (addTaskRequest.getImageId() != null) {
            try {
                Image uploadedImage = imageRepository.findById(addTaskRequest.getImageId())
                        .orElseThrow(() -> new IllegalStateException("Image not found"));
                uploadedImage.setTask(task); // Link the image with the task
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
    public void updateTask(UpdateTaskRequest updateTaskRequest, Long userId) {
        System.out.println("Update task id: " + updateTaskRequest.getId());
        Task task = new Task(
                updateTaskRequest.getId(),
                updateTaskRequest.getTitle(),
                updateTaskRequest.getDescription(),
                updateTaskRequest.getStatus(),
                updateTaskRequest.getCreatedAt(),
                userId
        );
//        if (updateTaskRequest.getImage() != null) {
//            try {
//                System.out.println("image file is not null");
//                imageService.deleteImage(task.getId());
//                Image uploadedImage = imageService.uploadImage(updateTaskRequest.getImage(), task);
//                task.setImage(uploadedImage);
//            } catch (Exception e) {
//                // Handle the exception
//                e.printStackTrace();
//                throw new IllegalStateException("Error uploading image");
//            }
//        } else {
//            System.out.println("image file is null");
//            imageService.deleteImage(task.getId());
//        }
        taskRepository.save(task);
    }
}