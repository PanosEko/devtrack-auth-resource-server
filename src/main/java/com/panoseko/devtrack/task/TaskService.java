package com.panoseko.devtrack.task;

import com.panoseko.devtrack.exception.ImageNotFoundException;
import com.panoseko.devtrack.exception.ImageProcessingException;
import com.panoseko.devtrack.exception.TaskNotFoundException;
import com.panoseko.devtrack.image.Image;
import com.panoseko.devtrack.image.ImageRepository;
import com.panoseko.devtrack.image.ImageUtils;
import com.panoseko.devtrack.image.ThumbnailDTO;
import com.panoseko.devtrack.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ImageRepository imageRepository;

    public List<TaskDTO> getTasks(Principal connectedUser) throws ImageProcessingException {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Task> tasks = taskRepository.findByUser(user);
        return tasks.stream()
                .map(task -> {
                    TaskDTO taskResponse = new TaskDTO(task);
                    Image image = task.getImage();
                    if (image != null) {
                        try {
                            ThumbnailDTO thumbnail = new ThumbnailDTO(image.getId().toString(),
                                    ImageUtils.decompress(image.getThumbnailData()));
                            taskResponse.setThumbnail(thumbnail);
                        } catch (DataFormatException | IOException e) {
                            throw new ImageProcessingException(
                                    "Failed to decompress thumbnail for image with parameters {id="
                                            + image.getId() + "}" + e.getMessage());
                        }
                    }
                    return taskResponse;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTask(Long taskId) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found for parameters {id=" + taskId + "}"));
        if (task.getImage() != null) {
            imageRepository.delete(task.getImage());
        }
        taskRepository.delete(task);
    }

    public void updateTaskStatus(Long taskId, Status status) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found for parameters {id=" + taskId + "}"));
        task.setStatus(status);
        taskRepository.save(task);
    }

    @Transactional
    public Long addTask(AddTaskRequestDTO addTaskRequest, Principal connectedUser)
            throws ImageNotFoundException {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Task task = new Task(
                addTaskRequest.getTitle(),
                addTaskRequest.getDescription(),
                addTaskRequest.getStatus(),
                addTaskRequest.getCreatedAt(),
                user
        );
        if (addTaskRequest.getImageId() != null) {
            Image uploadedImage = imageRepository.findById(addTaskRequest.getImageId())
                    .orElseThrow(() -> new ImageNotFoundException(
                            "Image not found for parameters {id=" + addTaskRequest.getImageId() + "}"));
            uploadedImage.setTask(task); // Link the image with the task
            task.setImage(uploadedImage);
        }
        return taskRepository.save(task).getId();
    }

    @Transactional
    public void updateTask(UpdateTaskRequestDTO updateTaskRequest)
            throws ImageNotFoundException, TaskNotFoundException {

        Task task = taskRepository.findById(updateTaskRequest.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found for parameters {id="
                        + updateTaskRequest.getId() + "}"));
        task.setTitle(updateTaskRequest.getTitle());
        task.setDescription(updateTaskRequest.getDescription());
        task.setStatus(updateTaskRequest.getStatus());
        task.setCreatedAt(updateTaskRequest.getCreatedAt());
        if (updateTaskRequest.getImageId() != null) {
            Image uploadedImage = imageRepository.findById(updateTaskRequest.getImageId())
                    .orElseThrow(() -> new ImageNotFoundException("Image not found for parameters {id="
                            + updateTaskRequest.getImageId() + "}"));
            uploadedImage.setTask(task); // Link the image with the task
            task.setImage(uploadedImage);

        }
        taskRepository.save(task);
    }

}
