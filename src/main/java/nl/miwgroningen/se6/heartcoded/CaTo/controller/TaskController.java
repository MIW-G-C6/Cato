package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.MemberService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskListService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.TaskService;
import nl.miwgroningen.se6.heartcoded.CaTo.service.UserService;
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
    private MemberService memberService;
    private UserService userService;

    public TaskController(TaskService taskService, TaskListService taskListService, MemberService memberService,
                          UserService userService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
        this.memberService = memberService;
        this.userService = userService;
    }

    @GetMapping("/groups/{groupId}/clientDashboard/{clientId}/taskLists/{taskListId}/{taskId}")
    protected String showTaskDetails(@PathVariable("groupId") Integer groupId,
                                     @PathVariable("clientId") Integer clientId,
                                     @PathVariable("taskListId") Integer taskListId,
                                     @PathVariable("taskId") Integer taskId, Model model) {

        if (doShowTaskDetailsOrTaskForm(taskListId, taskId, model)) {
            return "redirect:/groups/" + groupId + "/clientDashboard/" + clientId;
        }
        return "taskDetails";
    }

    @GetMapping("/groups/{groupId}/clientDashboard/{clientId}/taskLists/{taskListId}/update/{taskId}")
    protected String showUpdateTaskForm(@PathVariable("groupId") Integer groupId,
                                        @PathVariable("clientId") Integer clientId,
                                        @PathVariable("taskListId") Integer taskListId,
                                        @PathVariable("taskId") Integer taskId, Model model) {

        if (doShowTaskDetailsOrTaskForm(taskListId, taskId, model)) {
            return "redirect:/groups/" + groupId + "/clientDashboard/" + clientId;
        }
        return "taskForm";
    }

    @GetMapping("/groups/{groupId}/clientDashboard/{clientId}/taskLists/{taskListId}/new")
    protected String showTaskForm(@PathVariable("taskListId") Integer taskListId,
                                  @PathVariable("groupId") Integer groupId,
                                  @PathVariable("clientId") Integer clientId,  Model model) {
        Optional<TaskListDTO> taskListDTO = taskListService.findById(taskListId);

        if (taskListDTO.isEmpty()) {
            return "redirect:/groups/" + groupId + "/clientDashboard/" + clientId;
        }
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return "taskForm";
    }

    @PostMapping("/groups/{groupId}/clientDashboard/{clientId}/taskLists/{taskListId}/new")
    protected String saveOrUpdateTask(@PathVariable ("taskListId") Integer taskListId,
                                      @ModelAttribute("task") TaskDTO task, BindingResult result) {

        if (!result.hasErrors()) {
            taskService.save(task, taskListId);
        }
        return "redirect:/groups/{groupId}/clientDashboard/{clientId}";
    }

    @GetMapping("/task/delete/{taskId}")
    protected String deleteTask(@PathVariable("taskId") Integer taskId) {
        Integer clientId = taskListService.getById(taskService.getTaskListIdByTaskId(taskId)).getUserId();
        Integer groupId = memberService.getByClient(userService.getById(clientId)).getGroupId();
        taskService.deleteById(taskId);
        return "redirect:/groups/" + groupId + "/clientDashboard/" + clientId;
    }

    private boolean doShowTaskDetailsOrTaskForm(@PathVariable("taskListId") Integer taskListId,
                                                @PathVariable("taskId") Integer taskId, Model model) {
        Optional<TaskDTO> task = taskService.findById(taskId);
        if (task.isEmpty()) {
            return true;
        }
        //TODO model.addAttribute("group", taskListService) (vraag erwin naar juiste stappen)
        model.addAttribute("task", task.get());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return false;
    }
}

