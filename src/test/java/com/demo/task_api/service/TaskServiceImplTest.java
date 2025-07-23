package com.demo.task_api.service;

import com.demo.task_api.dto.TaskRequest;
import com.demo.task_api.dto.TaskResponse;
import com.demo.task_api.exception.ResourceNotFoundException;
import com.demo.task_api.model.Task;
import com.demo.task_api.repository.TaskRepository;
import com.demo.task_api.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private final LocalDate TEST_DATE = LocalDate.now().plusDays(7);
    private final String TEST_STATUS = "PENDING";

    private Task createSampleTask(Long id) {
        Task task = new Task();
        task.setId(id);
        task.setTitle("Complete project");
        task.setDescription("Finish all modules");
        task.setDueDate(TEST_DATE);
        task.setStatus(TEST_STATUS);
        return task;
    }

    private TaskRequest createSampleTaskRequest() {
        return new TaskRequest("Complete project", "Finish all modules", TEST_STATUS,TEST_DATE);
    }

    @Test
    void createTask_ShouldReturnTaskResponse() {
        // Arrange
        TaskRequest request = createSampleTaskRequest();
        Task savedTask = createSampleTask(1L);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);


        TaskResponse response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getDueDate(), response.getDueDate());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getAllTasks_ShouldReturnListOfTaskResponses() {
        Task task1 = createSampleTask(1L);
        Task task2 = createSampleTask(2L);

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<TaskResponse> responses = taskService.getAllTasks();

        assertEquals(2, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals(2L, responses.get(1).getId());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getAllTasks_ShouldReturnEmptyListWhenNoTasks() {

        when(taskRepository.findAll()).thenReturn(List.of());


        List<TaskResponse> responses = taskService.getAllTasks();


        assertTrue(responses.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_ShouldReturnTaskResponse() {

        Long taskId = 1L;
        Task task = createSampleTask(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));


        TaskResponse response = taskService.getTaskById(taskId);


        assertNotNull(response);
        assertEquals(taskId, response.getId());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTaskById_ShouldThrowWhenTaskNotFound() {

        Long taskId = 99L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTaskById(taskId);
        });
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void updateStatus_ShouldUpdateTaskStatus() {

        Long taskId = 1L;
        String newStatus = "COMPLETED";
        Task existingTask = createSampleTask(taskId);
        Task updatedTask = createSampleTask(taskId);
        updatedTask.setStatus(newStatus);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);


        TaskResponse response = taskService.updateStatus(taskId, newStatus);


        assertEquals(newStatus, response.getStatus());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void updateStatus_ShouldThrowWhenTaskNotFound() {

        Long taskId = 99L;
        String newStatus = "COMPLETED";

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.updateStatus(taskId, newStatus);
        });
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any());
    }


    @Test
    void deleteTaskById_ShouldThrowWhenTaskNotFound() {
        // Arrange
        Long taskId = 99L;

        when(taskRepository.existsById(taskId)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.deleteTaskById(taskId);
        });
        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, never()).deleteById(taskId);
    }

    @Test
    void deleteTaskById_ShouldThrowWhenDeletionFails() {
        // Arrange
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);
        when(taskRepository.existsById(taskId)).thenReturn(true); // Simulate deletion failure


        assertThrows(RuntimeException.class, () -> {
            taskService.deleteTaskById(taskId);
        });
    }

    @Test
    void deleteTaskById_ShouldDeleteTask() {

        Long taskId = 1L;


        when(taskRepository.existsById(taskId))
                .thenReturn(true)  // First call returns true (task exists)
                .thenReturn(false); // Second call returns false (after deletion)

        doNothing().when(taskRepository).deleteById(taskId);


        taskService.deleteTaskById(taskId);


        verify(taskRepository, times(2)).existsById(taskId); // Verify exactly 2 calls
        verify(taskRepository, times(1)).deleteById(taskId);
    }



}