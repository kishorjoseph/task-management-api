package com.demo.task_api.controller;

import com.demo.task_api.dto.StatusRequest;
import com.demo.task_api.dto.TaskRequest;
import com.demo.task_api.dto.TaskResponse;
import com.demo.task_api.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private final LocalDate TEST_DATE = LocalDate.now().plusDays(7);
    private final String TEST_STATUS = "PENDING";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private TaskRequest createSampleTaskRequest() {
        return new TaskRequest("Complete project", "Finish all modules", TEST_STATUS, TEST_DATE);
    }

    private TaskResponse createSampleTaskResponse(Long id) {
        return new TaskResponse(id, "Complete project", "Finish all modules", TEST_STATUS, TEST_DATE);
    }

    @Test
    void createTask_shouldReturnTaskCreatedWithLocalDate() {

        TaskRequest request = createSampleTaskRequest();
        TaskResponse mockResponse = createSampleTaskResponse(1L);
        when(taskService.createTask(request)).thenReturn(mockResponse);

        ResponseEntity<TaskResponse> response = taskController.createTask(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(TEST_DATE, response.getBody().getDueDate());
        verify(taskService, times(1)).createTask(request);
    }

    @Test
    void getAllTasks_ShouldReturnTasksWithLocalDates() {

        List<TaskResponse> mockResponses = Arrays.asList(
                createSampleTaskResponse(1L),
                createSampleTaskResponse(2L)
        );
        when(taskService.getAllTasks()).thenReturn(mockResponses);

        ResponseEntity<List<TaskResponse>> response = taskController.getAllTask();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TEST_DATE, response.getBody().get(0).getDueDate());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateTaskStatus_ShouldPreserveOriginalDate() {

        Long taskId = 1L;
        StatusRequest statusRequest = new StatusRequest("COMPLETED");
        TaskResponse originalResponse = createSampleTaskResponse(taskId);
        TaskResponse updatedResponse = new TaskResponse(
                taskId,
                originalResponse.getTitle(),
                originalResponse.getDescription(),
                "COMPLETED",
                originalResponse.getDueDate()
                );

        when(taskService.updateStatus(taskId, "COMPLETED")).thenReturn(updatedResponse);
        ResponseEntity<TaskResponse> response = taskController.updateTaskStatus(taskId, statusRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TEST_DATE, response.getBody().getDueDate());
        assertEquals("COMPLETED", response.getBody().getStatus());
    }

    @Test
    void createTask_ShouldHandleNullDate() {
        TaskRequest request = new TaskRequest("No date task", "Description", TEST_STATUS, null);
        when(taskService.createTask(request)).thenThrow(new IllegalArgumentException("Due date cannot be null"));
        assertThrows(IllegalArgumentException.class, () -> {
            taskController.createTask(request);
        });
    }

}