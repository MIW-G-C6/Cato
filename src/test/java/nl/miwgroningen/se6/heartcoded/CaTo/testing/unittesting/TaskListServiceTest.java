package nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.model.Circle;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.CircleRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.testing.unittesting.mappers.TaskListMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TaskListServiceTest {

    private TaskListService taskListService;
    private CircleRepository circleRepository;
    private TaskListMapper taskListMapper;
    private TaskListRepository taskListRepository;

    @BeforeEach
    void setUp() {
        taskListRepository = Mockito.mock(TaskListRepository.class);
        circleRepository = Mockito.mock(CircleRepository.class);
        taskListMapper = new TaskListMapper();
        taskListService = new TaskListService(taskListRepository, circleRepository, taskListMapper);
    }

    @Test
    void findAllTaskListsTest() {
        TaskList taskList1 = new TaskList(new Circle());
        TaskList taskList2 = new TaskList(new Circle());

        taskList1.setTaskListId(1);
        taskList2.setTaskListId(2);

        List<TaskList> taskLists = new ArrayList<>();
        taskLists.add(taskList1);
        taskLists.add(taskList2);

        when(taskListRepository.findAll()).thenReturn(taskLists);

        List<TaskListDTO> result = taskListService.findAllTaskLists();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getTaskListId());
        assertEquals(2, result.get(1).getTaskListId());
    }

    @Test
    void findTaskListByIdTest() {
        TaskList taskList = new TaskList(new Circle());
        taskList.setTaskListId(1);

        when(taskListRepository.findById(taskList.getTaskListId())).thenReturn(Optional.of(taskList));

        Optional<TaskListDTO> result = taskListService.findById(taskList.getTaskListId());

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getTaskListId());
    }

    @Test
    void getTaskListByIdTest() {
        TaskList taskList = new TaskList(new Circle(1, "testCircle", new ArrayList<>()));
        taskList.setTaskListId(1);

        when(taskListRepository.getById(1)).thenReturn(taskList);

        TaskListDTO result = taskListService.getById(1);

        assertEquals(1, result.getTaskListId());
        assertEquals(1, result.getCircleId());
    }

    @Test
    void saveTaskListTest() {
        Circle testCircle = new Circle(1, "testName", new ArrayList<>());
        TaskList testTaskList = new TaskList(testCircle);
        testTaskList.setTaskListId(1);

        when(circleRepository.getById(1)).thenReturn(testCircle);
        when(taskListRepository.save(testTaskList)).thenReturn(testTaskList);

        taskListService.save(taskListMapper.toDTO(testTaskList));

        Mockito.verify(taskListRepository, times(1)).save(any(TaskList.class));
    }

    @Test
    void getTaskListByCircleIdTest() {
        TaskList taskList = new TaskList(new Circle(3, "testCircle", new ArrayList<>()));
        taskList.setTaskListId(1);

        when(taskListRepository.getByCircleCircleId(3)).thenReturn(taskList);

        TaskListDTO result = taskListService.getByCircleId(3);

        assertNotNull(result);
        assertEquals(3, result.getCircleId());
        assertEquals(1, result.getTaskListId());
    }
}