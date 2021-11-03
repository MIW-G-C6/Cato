package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Controls all pages about task lists
 */

@Controller
public class TaskListController {

    private final TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping("/taskLists")
    protected String showTaskListOverview(Model model) {
        model.addAttribute("allTaskList", taskListService.findAll());
        return "taskListOverview";
    }

    @GetMapping("/taskLists/{taskListId}")
    protected String showTaskListDetails(@PathVariable("taskListId") Integer taskListId, Model model) {
        Optional<TaskListDTO> taskList = taskListService.findById(taskListId);
        if (taskList.isEmpty()) {
            return "redirect:/taskLists";
        }
        model.addAttribute("taskList", taskList.get());
        return "taskListDetails";
    }
}
