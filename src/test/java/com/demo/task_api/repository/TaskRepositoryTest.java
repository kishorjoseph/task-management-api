package com.demo.task_api.repository;


import com.demo.task_api.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task createTestTask() {
        Task task = new Task();
        task.setTitle("Complete project");
        task.setDescription("Finish all modules");
        task.setDueDate(LocalDate.now().plusDays(7));
        task.setStatus("PENDING");
        return task;
    }

    @Test
    void saveTask_ShouldPersistTask() {
        Task task = createTestTask();
        Task savedTask = taskRepository.save(task);
        assertNotNull(savedTask.getId());
        assertEquals("Complete project", savedTask.getTitle());
    }

    @Test
    void findById_ShouldReturnTask() {
        Task task = createTestTask();
        Task savedTask = taskRepository.save(task);
        Task foundTask = taskRepository.findById(savedTask.getId()).orElse(null);
        assertNotNull(foundTask);
        assertEquals(savedTask.getTitle(), foundTask.getTitle());
    }

    @Test
    void existsById_ShouldReturnTrueForExistingTask() {
        Task task = createTestTask();
        Task savedTask = taskRepository.save(task);
        assertTrue(taskRepository.existsById(savedTask.getId()));
    }

    @Test
    void deleteById_ShouldRemoveTask() {
        Task task = createTestTask();
        Task savedTask = taskRepository.save(task);
        taskRepository.deleteById(savedTask.getId());
        assertFalse(taskRepository.existsById(savedTask.getId()));
    }

}