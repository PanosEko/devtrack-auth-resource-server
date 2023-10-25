package com.panoseko.devtrack.task;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequest {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDate createdAt;
    private MultipartFile image;
}