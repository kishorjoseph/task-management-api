package com.demo.task_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class TaskRequest {
        @NotBlank(message = "Title cannot be empty")
        @Size(max = 100, message = "Title cannot exceed above 100 characters")
        private String title;
        private String description;
        @NotBlank(message = "Status cannot be empty")
        @Size(max = 20, message = "Status cannot exceed maximum of 20 characters")
        private String status;
        @NotNull(message = "Due date cannot be null")
        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate dueDate;

        public TaskRequest(String title, String description, String status, LocalDate dueDate) {
                this.title = title;
                this.description = description;
                this.status = status;
                this.dueDate = dueDate;

}

        public TaskRequest() {
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public LocalDate getDueDate() {
                return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
                this.dueDate = dueDate;
        }
}
