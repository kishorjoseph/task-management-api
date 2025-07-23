package com.demo.task_api.controller;

import com.demo.task_api.dto.StatusRequest;
import com.demo.task_api.dto.TaskRequest;
import com.demo.task_api.dto.TaskResponse;
import com.demo.task_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String home() {
        return "App is running! Access Swagger UI at: <a href='/swagger-ui.html'>/swagger-ui.html</a>";
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest taskRequest){
        TaskResponse taskCreated = taskService.createTask(taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTask(){
        List<TaskResponse> taskResponses = taskService.getAllTasks();
        return ResponseEntity.ok(taskResponses);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse response = taskService.getTaskById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable Long id, @RequestBody StatusRequest request) {
        TaskResponse response = taskService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

}
