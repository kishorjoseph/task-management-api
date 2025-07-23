package com.demo.task_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class StatusRequest {

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 20, message = "Status cannot exceed maximum of 20 characters")
    private  String status;

    public StatusRequest(String status) {
        this.status = status;
    }

    public StatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
