package com.demo.task_api.service;

import com.demo.task_api.dto.TaskRequest;
import com.demo.task_api.dto.TaskResponse;
import com.demo.task_api.model.Task;

import java.util.List;


public interface TaskService {
     TaskResponse createTask(TaskRequest request);
     List<TaskResponse> getAllTasks();
     TaskResponse getTaskById(Long id);
     void deleteTaskById(Long id);
     TaskResponse updateStatus(Long id, String status);
     TaskResponse updateTask(Task task);
}
