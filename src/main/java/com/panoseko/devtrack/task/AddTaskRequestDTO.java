package com.panoseko.devtrack.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskRequestDTO {
    @NotBlank(message = "title shouldn't be blank")
    private String title;
    private String description;
    @NotNull(message = "status shouldn't be null")
    private Status status;
    @NotNull(message = "date shouldn't be null")
    private LocalDate createdAt;
    private Long imageId;
}