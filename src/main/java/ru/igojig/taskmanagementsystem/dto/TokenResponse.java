package ru.igojig.taskmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String message;
    private String token;
}
