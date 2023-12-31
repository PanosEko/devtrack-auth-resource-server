package com.panoseko.devtrack.task;

import com.panoseko.devtrack.image.ThumbnailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDate createdAt;
    private ThumbnailDTO thumbnail;


    public TaskDTO(Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.createdAt = task.getCreatedAt();
    }

}


