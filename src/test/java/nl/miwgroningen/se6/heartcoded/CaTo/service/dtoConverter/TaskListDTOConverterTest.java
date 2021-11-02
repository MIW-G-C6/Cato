package nl.miwgroningen.se6.heartcoded.CaTo.service.dtoConverter;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListDTOConverterTest {

    private TaskListDTOConverter taskListDTOConverter;

    public TaskListDTOConverterTest(TaskListDTOConverter taskListDTOConverter) {
        this.taskListDTOConverter = taskListDTOConverter;
    }

    @Test
    void toTaskListDTOId() {
        TaskList taskList = new TaskList();
        taskList.setTaskListId(1);
        TaskListDTO taskListDTO = taskListDTOConverter.toDTO(taskList);
        assertEquals(1, taskListDTO.getTaskListId());
    }

    @Test
    void toTaskListDTOList() {
        TaskList taskList = new TaskList();
        List<Task> listOfTasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setDescription("testTask1");

        Task task2 = new Task();
        task2.setDescription("testTask2");

        listOfTasks.add(task1);
        listOfTasks.add(task2);

        taskList.setTaskList(listOfTasks);
        TaskListDTO taskListDTO = taskListDTOConverter.toDTO(taskList);
        assertEquals("testTask1", taskListDTO.getTaskList().get(0).getDescription());
        assertEquals("testTask2", taskListDTO.getTaskList().get(1).getDescription());
    }

    @Test
    void toTaskListDTOClient() {
        TaskList taskList = new TaskList();
        User client = new User();
        client.setName("testName");
        taskList.setClient(client);
        TaskListDTO taskListDTO = taskListDTOConverter.toDTO(taskList);
        assertEquals("testName", taskListDTO.getClient().getName());
    }

    @Test
    void toTaskListModelId() {
        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setTaskListId(1);
        TaskList taskList = taskListDTOConverter.toModel(taskListDTO);
        assertEquals(1, taskList.getTaskListId());
    }

    @Test
    void toTaskListModelList() {
        TaskListDTO taskListDTO = new TaskListDTO();
        List<TaskDTO> listOfTasksDTO = new ArrayList<>();

        TaskDTO task1 = new TaskDTO();
        task1.setDescription("testTask1");

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("testTask2");

        listOfTasksDTO.add(task1);
        listOfTasksDTO.add(task2);

        taskListDTO.setTaskList(listOfTasksDTO);
        TaskList taskList= taskListDTOConverter.toModel(taskListDTO);
        assertEquals("testTask1", taskList.getTaskList().get(0).getDescription());
        assertEquals("testTask2", taskList.getTaskList().get(1).getDescription());
    }

    @Test
    void toTaskListModelClient() {
        TaskListDTO taskListDTO = new TaskListDTO();
        UserDTO client = new UserDTO();
        client.setName("testName");
        taskListDTO.setClient(client);
        TaskList taskList = taskListDTOConverter.toModel(taskListDTO);
        assertEquals("testName", taskList.getClient().getName());
    }
}