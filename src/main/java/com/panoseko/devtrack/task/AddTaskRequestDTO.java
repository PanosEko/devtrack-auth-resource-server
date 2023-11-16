package com.panoseko.devtrack.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskRequestDTO {
    private String title;
    private String description;
    private Status status;
    private LocalDate createdAt;
    private Long imageId;
}