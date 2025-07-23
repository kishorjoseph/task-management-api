package com.demo.task_api.service.impl;

import com.demo.task_api.dto.TaskRequest;
import com.demo.task_api.dto.TaskResponse;
import com.demo.task_api.exception.ResourceNotFoundException;
import com.demo.task_api.model.Task;
import com.demo.task_api.repository.TaskRepository;
import com.demo.task_api.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task newTask = new Task();
        newTask.setTitle(taskRequest.getTitle());
        newTask.setStatus(taskRequest.getStatus());
        newTask.setDescription(taskRequest.getDescription());
        newTask.setDueDate(taskRequest.getDueDate());
        Task taskCreated = taskRepository.save(newTask);
        return mapToRecord(taskCreated);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::mapToRecord)
                .collect(Collectors.toList());
    }


    @Override
    public TaskResponse getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            return mapToRecord(taskOptional.get());
        } else {
            throw new ResourceNotFoundException("Task with Id " + id + " not found");
        }
    }

    @Override
    public void deleteTaskById(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task with ID " + id + " not available");
        }
        taskRepository.deleteById(id);
        if (taskRepository.existsById(id)) {
            throw new RuntimeException("Problem encountered while deleting the task with id " + id);
        }
    }

    @Override
    public TaskResponse updateStatus(Long id, String status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setStatus(status);
        Task taskUpdated = taskRepository.save(task);
        return mapToRecord(taskUpdated);
    }

    @Override
    public TaskResponse updateTask(Task task) {
        return null;
    }

    private TaskResponse mapToRecord(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setDueDate(task.getDueDate());
        return response;
    }
}
