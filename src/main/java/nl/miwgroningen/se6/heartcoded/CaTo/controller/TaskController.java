package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls all pages about tasks
 */
@Controller
public class TaskController {

    private TaskService taskService;
    private TaskListService taskListService;

    public TaskController(TaskService taskService, TaskListService taskListService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
    }

    @GetMapping("/taskLists/{taskListId}/new")
    protected String showTaskForm(@PathVariable("taskListId") Integer taskListId, Model model) {
        Optional<TaskList> taskList = taskListService.findById(taskListId);
        if (taskList.isEmpty()) {
            return "redirect:/taskLists";
        }
        model.addAttribute("task", new Task());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return "taskForm";
    }

    @GetMapping("/task/delete/{taskId}")
    protected String deleteTask(@PathVariable("taskId") Integer taskId) {
        Integer taskListId = taskService.getTaskListIdByTaskId(taskId);
        taskService.deleteById(taskId);
        return "redirect:/taskLists/" + taskListId;
    }

    @PostMapping("/taskLists/{taskListId}/new")
    protected String saveOrUpdateTask(
            @PathVariable ("taskListId") Integer taskListId,
            @ModelAttribute("task") Task task, BindingResult result) {

        if (!result.hasErrors()) {
            task.setTaskList(taskListService.getById(taskListId));
            taskService.save(task);
        }
        return "redirect:/taskLists/{taskListId}";
    }
}
