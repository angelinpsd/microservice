package com.devops.microservice.dto;

public class ResponseDTO {
    private String message;

    public ResponseDTO() {}

    public ResponseDTO(String message) {
        this.message = message;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}