package com.panoseko.devtrack.task;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequestDTO {
    @NotNull(message = "id shouldn't be null")
    private Long id;
    @NotBlank(message = "title shouldn't be blank")
    private String title;
    private String description;
    @NotNull(message = "status shouldn't be null")
    private Status status;
    private LocalDate createdAt;
    private Long imageId;
}