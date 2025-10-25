package com.devops.microservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequestDTO {

    @NotBlank(message = "message is required")
    private String message;

    @NotBlank(message = "to is required")
    private String to;

    @NotBlank(message = "from is required")
    private String from;

    @NotNull(message = "timeToLifeSec is required")
    private Integer timeToLifeSec;

    // Constructors
    public RequestDTO() {}

    public RequestDTO(String message, String to, String from, Integer timeToLifeSec) {
        this.message = message;
        this.to = to;
        this.from = from;
        this.timeToLifeSec = timeToLifeSec;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public Integer getTimeToLifeSec() { return timeToLifeSec; }
    public void setTimeToLifeSec(Integer timeToLifeSec) { this.timeToLifeSec = timeToLifeSec; }
}