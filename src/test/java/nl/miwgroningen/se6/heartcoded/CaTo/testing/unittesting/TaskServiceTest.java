package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.UserRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskService;
import nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskListRepository taskListRepository;
    private UserRepository userRepository;

    private TaskMapper taskMapper;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskListRepository = Mockito.mock(TaskListRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        taskMapper = new TaskMapper();

        taskService = new TaskService(taskRepository, taskListRepository, userRepository, taskMapper);
    }

    @Test
    void findAllTasksTest() {
        List<Task> listOfTasks = new ArrayList<>();
        listOfTasks.add(new Task(1, "testTask1", new TaskList(), "Low", new User()));
        listOfTasks.add(new Task(2, "testTask2", new TaskList(), "Low", new User()));

        when(taskRepository.findAll()).thenReturn(listOfTasks);

        List<TaskDTO> result = taskService.findAllTasks();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getTaskId());
        assertEquals(2, result.get(1).getTaskId());
    }

    @Test
    void findTaskByIdTest() {
        Task task = new Task(1, "testTask1", new TaskList(), "low", new User());

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Optional<TaskDTO> result = taskService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getTaskId());
        assertEquals("testTask1", result.get().getDescription());
        assertEquals("low", result.get().getPriority());
    }

    @Test
    void getTaskListByTaskIdTest() {
        TaskList testTaskList = new TaskList();
        testTaskList.setTaskListId(3);

        Task testTask = new Task(1, "testDescription", testTaskList, "high", new User());

        when(taskRepository.getById(1)).thenReturn(testTask);

        Integer result = taskService.getTaskListIdByTaskId(1);

        assertNotNull(result);
        assertEquals(3, result);
    }
}