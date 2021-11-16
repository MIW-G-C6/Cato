package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.TaskMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
    }

    @Test
    void toDTO() {
        Task task = new Task();
        task.setTaskId(1);
        task.setDescription("TEST TEST");
        task.setPriority("High");
        task.setTaskList(new TaskList());

        TaskDTO taskDTO = taskMapper.toDTO(task);

        assertEquals("TEST TEST", taskDTO.getDescription());
        assertEquals("High", taskDTO.getPriority());
        assertEquals(1, taskDTO.getTaskId());
    }

    @Test
    void presentOptionalToDTOTest() {
        Task task = new Task();
        task.setTaskId(1);
        task.setDescription("TEST TEST");
        task.setPriority("High");
        task.setTaskList(new TaskList());

        Optional<Task> presentOptionalTask = Optional.of(task);

        Optional<TaskDTO> presentOptionalDTO = taskMapper.toDTO(presentOptionalTask);

        assertTrue(presentOptionalDTO.isPresent());
        assertEquals(1, presentOptionalDTO.get().getTaskId());
        assertEquals("TEST TEST", presentOptionalDTO.get().getDescription());
        assertEquals("High", presentOptionalDTO.get().getPriority());
    }

    @Test
    void emptyOptionalToDTOTest() {
        Optional<Task> emptyOptionalTask = Optional.empty();

        Optional<TaskDTO> emptyOptionalDTO = taskMapper.toDTO(emptyOptionalTask);

        assertTrue(emptyOptionalDTO.isEmpty());
    }

    @Test
    void toTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskListId(1);
        taskDTO.setTaskId(2);
        taskDTO.setDescription("TEST TEST");
        taskDTO.setPriority("Low");

        Task task = taskMapper.toTask(taskDTO);

        assertEquals(2, task.getTaskId());
        assertEquals("TEST TEST", task.getDescription());
        assertEquals("Low", task.getPriority());
    }
}