package nl.miwgroningen.se6.heartcoded.CaTo.controller;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.dto.TaskListDTO;
import nl.miwgroningen.se6.heartcoded.CaTo.service.*;
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
 * Controls all pages about tasks.
 */

@Controller
public class TaskController {

    private TaskService taskService;
    private TaskListService taskListService;
    private CircleService circleService;
    private MemberService memberService;
    private UserService userService;

    public TaskController(TaskService taskService, TaskListService taskListService, CircleService circleService,
                          MemberService memberService, UserService userService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
        this.circleService = circleService;
        this.memberService = memberService;
        this.userService = userService;
    }

    @GetMapping("/circles/{circleId}/taskLists/{taskListId}/{taskId}")
    protected String showTaskDetails(@PathVariable("circleId") Integer circleId,
                                     @PathVariable("taskListId") Integer taskListId,
                                     @PathVariable("taskId") Integer taskId, Model model) {
        if (!memberService.userIsMemberOfCircle(circleId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        if (doShowTaskDetailsOrTaskForm(taskListId, taskId, model)) {
            return "redirect:/circles/" + circleId;
        }

        model.addAttribute("circleName", circleService.getById(circleId).getCircleName());
        return "taskDetails";
    }

    @GetMapping("/circles/{circleId}/taskLists/{taskListId}/update/{taskId}")
    protected String showUpdateTaskForm(@PathVariable("circleId") Integer circleId,
                                        @PathVariable("taskListId") Integer taskListId,
                                        @PathVariable("taskId") Integer taskId, Model model) {

        if (!memberService.userIsMemberOfCircle(circleId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        if (doShowTaskDetailsOrTaskForm(taskListId, taskId, model)) {
            return "redirect:/circles/" + circleId;
        }

        model.addAttribute("circleName", circleService.getById(circleId).getCircleName());
        return "taskEdit";
    }

    @GetMapping("/circles/{circleId}/taskLists/{taskListId}/new")
    protected String showTaskForm(@PathVariable("taskListId") Integer taskListId,
                                  @PathVariable("circleId") Integer circleId, Model model) {
        if (!memberService.userIsMemberOfCircle(circleId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        Optional<TaskListDTO> taskListDTO = taskListService.findById(taskListId);

        if (taskListDTO.isEmpty()) {
            return "redirect:/circles/" + circleId;
        }

        model.addAttribute("task", new TaskDTO());
        model.addAttribute("circleName", circleService.getById(circleId).getCircleName());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return "taskForm";
    }

    @GetMapping("/circles/{circleId}/task/{taskId}/assign")
    protected String assignTask(@PathVariable ("circleId") Integer circleId,
                                @PathVariable ("taskId") Integer taskId) {
        if (!memberService.userIsMemberOfCircle(circleId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        taskService.assignUser(taskId, userService.getCurrentUser().getUserId());
        return "redirect:/circles/{circleId}";
    }

    @GetMapping("/circles/{circleId}/task/{taskId}/unassign")
    protected String unassignTask(@PathVariable ("circleId") Integer circleId,
                                @PathVariable ("taskId") Integer taskId) {
        if (!memberService.userIsMemberOfCircle(circleId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        taskService.unassignUser(taskId);
        return "redirect:/circles/{circleId}";
    }

    @PostMapping("/circles/{circleId}/taskLists/{taskListId}/new")
    protected String saveOrUpdateTask(@PathVariable ("taskListId") Integer taskListId,
                                      @PathVariable("circleId") Integer circleId,
                                      @ModelAttribute("task") TaskDTO task, BindingResult result) {
        if (!memberService.userIsMemberOfCircle(circleId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        if (!result.hasErrors()) {
            taskService.save(task, taskListId);
        }

        return "redirect:/circles/{circleId}";
    }

    @GetMapping("/task/delete/{taskId}")
    protected String deleteTask(@PathVariable("taskId") Integer taskId) {
        Integer circleId = taskListService.getById(taskService.getTaskListIdByTaskId(taskId)).getCircleId();

        if (!memberService.userIsMemberOfCircle(circleId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }

        taskService.deleteById(taskId);
        return "redirect:/circles/" + circleId;
    }

    private boolean doShowTaskDetailsOrTaskForm(@PathVariable("taskListId") Integer taskListId,
                                                @PathVariable("taskId") Integer taskId, Model model) {
        Optional<TaskDTO> task = taskService.findById(taskId);
        if (task.isEmpty()) {
            return true;
        }

        model.addAttribute("task", task.get());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return false;
    }
}

