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
 * Controls all pages about tasks
 */

@Controller
public class TaskController {

    private TaskService taskService;
    private TaskListService taskListService;
    private GroupService groupService;
    private MemberService memberService;
    private UserService userService;

    public TaskController(TaskService taskService, TaskListService taskListService, GroupService groupService,
                          MemberService memberService, UserService userService) {
        this.taskService = taskService;
        this.taskListService = taskListService;
        this.groupService = groupService;
        this.memberService = memberService;
        this.userService = userService;
    }

    @GetMapping("/groups/{groupId}/taskLists/{taskListId}/{taskId}")
    protected String showTaskDetails(@PathVariable("groupId") Integer groupId,
                                     @PathVariable("taskListId") Integer taskListId,
                                     @PathVariable("taskId") Integer taskId, Model model) {

        if (!memberService.userIsMemberOfGroup(groupId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        if (doShowTaskDetailsOrTaskForm(taskListId, taskId, model)) {
            return "redirect:/groups/" + groupId;
        }
        model.addAttribute("groupName", groupService.getById(groupId).getGroupName());
        return "taskDetails";
    }

    @GetMapping("/groups/{groupId}/taskLists/{taskListId}/update/{taskId}")
    protected String showUpdateTaskForm(@PathVariable("groupId") Integer groupId,
                                        @PathVariable("taskListId") Integer taskListId,
                                        @PathVariable("taskId") Integer taskId, Model model) {

        if (!memberService.userIsMemberOfGroup(groupId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        if (doShowTaskDetailsOrTaskForm(taskListId, taskId, model)) {
            return "redirect:/groups/" + groupId;
        }
        model.addAttribute("groupName", groupService.getById(groupId).getGroupName());
        return "taskForm";
    }

    @GetMapping("/groups/{groupId}/taskLists/{taskListId}/new")
    protected String showTaskForm(@PathVariable("taskListId") Integer taskListId,
                                  @PathVariable("groupId") Integer groupId,
                                  Model model) {
        if (!memberService.userIsMemberOfGroup(groupId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        Optional<TaskListDTO> taskListDTO = taskListService.findById(taskListId);

        if (taskListDTO.isEmpty()) {
            return "redirect:/groups/" + groupId;
        }
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("groupName", groupService.getById(groupId).getGroupName());
        model.addAttribute("taskList", taskListService.getById(taskListId));
        return "taskForm";
    }

    @GetMapping("/groups/{groupId}/task/{taskId}/assign")
    protected String assignTask(@PathVariable ("groupId") Integer groupId,
                                @PathVariable ("taskId") Integer taskId) {
        if (!memberService.userIsMemberOfGroup(groupId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        taskService.assignUser(taskId, userService.getCurrentUser().getUserId());

        return "redirect:/groups/{groupId}";
    }

    @GetMapping("/groups/{groupId}/task/{taskId}/unassign")
    protected String unassignTask(@PathVariable ("groupId") Integer groupId,
                                @PathVariable ("taskId") Integer taskId) {
        if (!memberService.userIsMemberOfGroup(groupId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        taskService.unassignUser(taskId);

        return "redirect:/groups/{groupId}";
    }

    @PostMapping("/groups/{groupId}/taskLists/{taskListId}/new")
    protected String saveOrUpdateTask(@PathVariable ("taskListId") Integer taskListId,
                                      @PathVariable("groupId") Integer groupId,
                                      @ModelAttribute("task") TaskDTO task, BindingResult result) {

        if (!memberService.userIsMemberOfGroup(groupId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        if (!result.hasErrors()) {
            taskService.save(task, taskListId);
        }
        return "redirect:/groups/{groupId}";
    }

    @GetMapping("/task/delete/{taskId}")
    protected String deleteTask(@PathVariable("taskId") Integer taskId) {
        Integer groupId = taskListService.getById(taskService.getTaskListIdByTaskId(taskId)).getGroupId();
        if (!memberService.userIsMemberOfGroup(groupId) && !userService.currentUserIsSiteAdmin()) {
            return "redirect:/403";
        }
        taskService.deleteById(taskId);
        return "redirect:/groups/" + groupId;
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

