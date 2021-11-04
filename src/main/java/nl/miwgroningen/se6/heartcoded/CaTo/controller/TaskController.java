package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.MemberDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.UserDTO;
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

    public TaskController(TaskService taskService, TaskListService taskListService, MemberService memberService, UserService userService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
        this.memberService = memberService;
        this.userService = userService;
    }

    @GetMapping("/taskLists/{taskListId}/{taskId}")
    protected String showTaskDetails(@PathVariable("taskListId") Integer taskListId, @PathVariable("taskId") Integer taskId, Model model) {

        Optional<TaskDTO> task = taskService.findById(taskId);
        if (task.isEmpty()) {
            return "redirect:/taskLists/" + taskListId;
        }
        model.addAttribute("task", task.get());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return "taskDetails";
    }

    @GetMapping("taskLists/{taskListId}/update/{taskId}")
    protected String showUpdateTaskForm(@PathVariable("taskListId") Integer taskListId, @PathVariable("taskId") Integer taskId, Model model) {
        Optional<TaskDTO> task = taskService.findById(taskId);
        if (task.isEmpty()) {
            return "redirect:/taskLists/" + taskListId;
        }
        model.addAttribute("task", task.get());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return "taskForm";
    }

    @GetMapping("/groups/{groupId}/clientDashboard/{clientId}/taskLists/{taskListId}/new")
    protected String showTaskForm(@PathVariable("taskListId") Integer taskListId,
                                  @PathVariable("groupId") Integer groupId,
                                  @PathVariable("clientId") Integer clientId,  Model model) {
        Optional<TaskListDTO> taskListDTO = taskListService.findById(taskListId);

        if (taskListDTO.isEmpty()) {
            return "redirect:/groups/{groupId}/clientDashboard/{clientId}";
        }
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return "taskForm";
    }

    @PostMapping("/groups/{groupId}/clientDashboard/{clientId}/taskLists/{taskListId}/new")
    protected String saveOrUpdateTask(
            @PathVariable ("taskListId") Integer taskListId,
            @ModelAttribute("task") TaskDTO task, Model model, BindingResult result) {

        if (!result.hasErrors()) {
            taskService.save(task, taskListId);
        }
        return "redirect:/groups/{groupId}/clientDashboard/{clientId}";
    }

    @GetMapping("/task/delete/{taskId}")
    protected String deleteTask(@PathVariable("taskId") Integer taskId) {
        Integer taskListId = taskService.getTaskListIdByTaskId(taskId);
        TaskListDTO taskList = taskListService.getById(taskListId);
        UserDTO client = userService.getById(taskList.getUserId());
        MemberDTO clientMember = memberService.getByClient(client);
        taskService.deleteById(taskId);
        return "redirect:/groups/" + clientMember.getGroupId() + "/clientDashboard/" + taskList.getUserId();
    }
}

