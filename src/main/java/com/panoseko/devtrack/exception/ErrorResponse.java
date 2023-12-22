package com.panoseko.devtrack.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private HttpStatus status;
    private int errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;
    private Map<String, String> errorDetails;

}
