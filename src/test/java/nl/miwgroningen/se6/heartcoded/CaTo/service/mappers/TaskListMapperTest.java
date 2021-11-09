package nl.miwgroningen.se6.heartcoded.CaTo.service.mappers;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Group;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskListMapperTest {

    private TaskListMapper taskListMapper;

    @BeforeEach
    void setUp() {
        taskListMapper = new TaskListMapper();
    }

    @Test
    void toDTOTaskListId() {
        TaskList taskList = new TaskList();
        taskList.setTaskListId(1);
        taskList.setGroup(new Group());
        TaskListDTO taskListDTO = taskListMapper.toDTO(taskList);
        assertEquals(1, taskListDTO.getTaskListId());
    }

    @Test
    void toDTOGroupId() {
        TaskList taskList = new TaskList();
        Group group = new Group();
        group.setGroupId(100);

        taskList.setGroup(group);

        TaskListDTO taskListDTO = taskListMapper.toDTO(taskList);
        assertEquals(100, taskListDTO.getGroupId());
    }

    @Test
    void toDTOList() {
        List<TaskList> taskList = new ArrayList<>();

        TaskList taskList1 = new TaskList();
        taskList1.setTaskListId(1);

        TaskList taskList2 = new TaskList();
        taskList2.setTaskListId(2);

        taskList.add(taskList1);
        taskList.add(taskList2);

        taskList.get(0).setGroup(new Group());
        taskList.get(1).setGroup(new Group());

        List<TaskListDTO> taskListDTOList = new ArrayList<>();
        for (TaskList task : taskList) {
            taskListDTOList.add(taskListMapper.toDTO(task));
        }

        assertEquals(1, taskListDTOList.get(0).getTaskListId());
        assertEquals(2, taskListDTOList.get(1).getTaskListId());
    }

    @Test
    void toModelTaskListId() {
        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setTaskListId(1);
        TaskList taskList = taskListMapper.toTaskList(taskListDTO);
        assertEquals(1, taskList.getTaskListId());
    }

    @Test
    void emptyOptionalToDTO() {
        Optional<TaskList> optionalTaskList = Optional.empty();
        Optional<TaskListDTO> optionalTaskListDTO = taskListMapper.toDTO(optionalTaskList);
        assertTrue(optionalTaskListDTO.isEmpty());
    }

    @Test
    void presentOptionalToDTO() {
        TaskList taskList = new TaskList();

        Group group = new Group();
        group.setGroupId(1);

        taskList.setTaskListId(1);
        taskList.setGroup(group);

        Optional<TaskList> optionalTaskList = Optional.of(taskList);
        Optional<TaskListDTO> optionalTaskListDTO = taskListMapper.toDTO(optionalTaskList);

        assertTrue(optionalTaskListDTO.isPresent());
        assertEquals(1, optionalTaskListDTO.get().getGroupId());
        assertEquals(1, optionalTaskListDTO.get().getTaskListId());
    }
}