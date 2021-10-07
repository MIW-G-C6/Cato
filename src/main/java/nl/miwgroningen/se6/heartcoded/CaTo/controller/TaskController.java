package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.model.Task;
import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskList;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskRepository;
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

    TaskRepository taskRepository;
    TaskListRepository taskListRepository;

    public TaskController(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @GetMapping("/taskLists/{taskListId}/new")
    protected String showTaskForm(@PathVariable("taskListId") Integer taskListId, Model model) {
        Optional<TaskList> taskList = taskListRepository.findById(taskListId);
        if (taskList.isEmpty()) {
            return "redirect:/taskLists";
        }
        model.addAttribute("task", new Task());
        model.addAttribute("taskList", taskListRepository.getById(taskListId));
        return "taskForm";
    }

    @GetMapping("/task/delete/{taskId}")
    protected String deleteTask(@PathVariable("taskId") Integer taskId) {
        Integer temp = taskRepository.getById(taskId).getTaskList().getTaskListId();
        taskRepository.deleteById(taskId);
        return "redirect:/taskLists/" + temp;
    }

    @PostMapping("/taskLists/{taskListId}/new")
    protected String saveOrUpdateTask(
            @PathVariable ("taskListId") Integer taskListId,
            @ModelAttribute("task") Task task, BindingResult result) {

        if (!result.hasErrors()) {
            task.setTaskList(taskListRepository.getById(taskListId));
            taskRepository.save(task);
        }
        return "redirect:/taskLists/{taskListId}";
    }
}
