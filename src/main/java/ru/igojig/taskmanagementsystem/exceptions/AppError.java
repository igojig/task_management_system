package ru.igojig.taskmanagementsystem.exceptions;

import lombok.Getter;

@Getter
public class AppError extends RuntimeException{
    private final String message;

    public AppError(String message) {
        super(message);
        this.message = message;
    }
}
