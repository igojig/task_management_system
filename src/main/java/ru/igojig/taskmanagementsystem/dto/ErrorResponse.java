package ru.igojig.taskmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
