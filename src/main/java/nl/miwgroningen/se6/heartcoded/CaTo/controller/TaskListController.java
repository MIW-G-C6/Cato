package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskListRepository;
import nl.miwgroningen.se6.heartcoded.CaTo.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls all pages about task lists
 */

@Controller
public class TaskListController {

    TaskRepository taskRepository;
    TaskListRepository taskListRepository;

    public TaskListController(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @GetMapping("/taskLists")
    public String showTaskListOverview(Model model) {
        model.addAttribute("allTaskList", taskListRepository.findAll());
        return "taskListOverview";
    }
}
