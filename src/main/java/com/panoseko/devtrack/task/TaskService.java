package com.panoseko.devtrack.task;

import com.panoseko.devtrack.image.Image;
import com.panoseko.devtrack.image.ThumbnailDTO;
import com.panoseko.devtrack.image.ImageRepository;
import com.panoseko.devtrack.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<TaskResponseDTO> getTasks(Long userId) {
        List<Task> tasks = taskRepository.findTasksByCreator(userId);
        List<TaskResponseDTO> tasksResponses = new ArrayList<>();
        for (Task task : tasks) {
            TaskResponseDTO taskResponse = new TaskResponseDTO(task);
            Image image = task.getImage();
            if (image != null) {
                ThumbnailDTO thumbnail = new ThumbnailDTO(image.getId().toString(),
                        imageService.decompressImageData(image.getThumbnailData()));
                taskResponse.setThumbnail(thumbnail);
            }
            tasksResponses.add(taskResponse);
        }
        return tasksResponses;
    }



    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            imageService.deleteImageByTaskId(taskId);
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

    @Transactional
    public Long addTask(AddTaskRequestDTO addTaskRequest, Long userId) {
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
                e.printStackTrace();
                throw new IllegalStateException("Error uploading image");
            }
        }
        return taskRepository.save(task).getId();
    }

    public void updateTask(UpdateTaskRequestDTO updateTaskRequest) {
        Task task = taskRepository.findById(updateTaskRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        // Update the task properties
        task.setTitle(updateTaskRequest.getTitle());
        task.setDescription(updateTaskRequest.getDescription());
        task.setStatus(updateTaskRequest.getStatus());
        task.setCreatedAt(updateTaskRequest.getCreatedAt());
        if (updateTaskRequest.getImageId() != null) {
            try {
                // Delete the old image if it exists
                Optional<Image> oldImage = imageRepository.findImageByTask(task.getId());
                if (oldImage.isPresent() && !Objects.equals(oldImage.get().getId(), updateTaskRequest.getImageId())) {
                    imageService.deleteImage(oldImage.get().getId());
                }
                Image uploadedImage = imageRepository.findById(updateTaskRequest.getImageId())
                        .orElseThrow(() -> new IllegalStateException("Image not found"));
                uploadedImage.setTask(task); // Link the image with the task
                task.setImage(uploadedImage);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException("Error uploading image");
            }
        }
        taskRepository.save(task);

    }

    //    @Transactional
//    public void updateTask(UpdateTaskRequestDTO updateTaskRequest, Long userId) {
//
//        Task task = new Task(
//                updateTaskRequest.getId(),
//                updateTaskRequest.getTitle(),
//                updateTaskRequest.getDescription(),
//                updateTaskRequest.getStatus(),
//                updateTaskRequest.getCreatedAt(),
//                userId
//        );
//        if (updateTaskRequest.getImageId() != null) {
//            try {
//                Image uploadedImage = imageRepository.findById(updateTaskRequest.getImageId())
//                        .orElseThrow(() -> new IllegalStateException("Image not found"));
//                uploadedImage.setTask(task); // Link the image with the task
//                task.setImage(uploadedImage);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new IllegalStateException("Error uploading image");
//            }
//        }
//        taskRepository.save(task);
//    }
}