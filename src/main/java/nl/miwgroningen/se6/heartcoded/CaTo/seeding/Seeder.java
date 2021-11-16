package nl.miwgroningen.se6.heartcoded.CaTo.seeding;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.*;
import nl.miwgroningen.se6.heartcoded.CaTo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Test data for the database.
 */

@Component
public class Seeder {

    private UserService userService;
    private CircleService circleService;
    private MemberService memberService;
    private TaskService taskService;
    private TaskListService taskListService;

    @Autowired
    public Seeder(UserService userService, CircleService circleService, MemberService memberService,
                  TaskService taskService, TaskListService taskListService) {
        this.userService = userService;
        this.circleService = circleService;
        this.memberService = memberService;
        this.taskService = taskService;
        this.taskListService = taskListService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (userService.findAllUsers().size() == 0) {
            seedUser();
        }

        if (circleService.findAllCircles().size() == 0) {
            seedCircles();
            if (taskListService.findAllTaskLists().size() == 0) {
                seedTaskLists();
            }
        }

        if (memberService.findAllMembers().size() == 0) {
            seedMembers();
        }

        if (taskService.findAllTasks().size() == 0) {
            seedTasks();
        }

        if (!userService.emailInUse("admin@admin.com")) {
            userService.saveAdmin();
        }
    }

    private void seedUser() {
        userService.saveNewUser(new UserRegistrationDTO("Piet Bakker", "a", "a", "piet@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Henk Janssen", "a", "a", "henk@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Klaas Smit", "a", "a", "klaas@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Willem Visser", "a", "a", "willem@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Noah van den Berg", "a", "a", "noah@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Bram Snor", "a", "a", "bram@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Aart Lutjes", "a", "a", "aart@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Steven van der Gracht", "a", "a", "steven@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Niels Wolkers", "a", "a", "niels@example.com"));
        userService.saveNewUser(new UserRegistrationDTO("Wouter Geestra", "a", "a", "wouter@example.com"));

        userService.saveNewUser(new UserRegistrationDTO("Harry Carroll", "h3;#DvVwr[", "h3;#DvVwr[", "keijser@comcast.net"));
        userService.saveNewUser(new UserRegistrationDTO("Jaydn Wiggins", "H/V86P7s=v", "H/V86P7s=v", "eimear@outlook.com"));
        userService.saveNewUser(new UserRegistrationDTO("Farzana Meadows", "fmRL<3*9%^", "fmRL<3*9%^", "report@live.com"));
        userService.saveNewUser(new UserRegistrationDTO("Kareem Dalton", "H[jK!kR65m", "H[jK!kR65m", "kiddailey@yahoo.com"));
        userService.saveNewUser(new UserRegistrationDTO("Nazia Barton", "rqf2!P.L8v", "rqf2!P.L8v", "sravani@gmail.com"));
        userService.saveNewUser(new UserRegistrationDTO("Mea Irwin", "RAv]dt;Q9*", "RAv]dt;Q9*", "gavinls@yahoo.com"));
        userService.saveNewUser(new UserRegistrationDTO("Janae Dillon", "f=Xu/#9?Ze", "f=Xu/#9?Ze", "malvar@icloud.com"));
        userService.saveNewUser(new UserRegistrationDTO("Ayden Adkins", "Xh4H)]eYcq", "Xh4H)]eYcq", "peterhoeg@live.com"));
        userService.saveNewUser(new UserRegistrationDTO("Anabelle Hickman", "P6;H7Q%xz-", "P6;H7Q%xz-", "arandal@optonline.net"));
        userService.saveNewUser(new UserRegistrationDTO("Rhona Bradshaw", "C2t(VE4^kK", "C2t(VE4^kK", "raides@gmail.com"));
        userService.saveNewUser(new UserRegistrationDTO("Olivier Clay", "y9W>4grj]L%6!Je", "y9W>4grj]L%6!Je", "rkobes@verizon.net"));
        userService.saveNewUser(new UserRegistrationDTO("Wilma Rasmussen", "v,:]PMc4eGS{gx9", "v,:]PMc4eGS{gx9",  "bockelboy@hotmail.com"));
        userService.saveNewUser(new UserRegistrationDTO("Cathy Mcgee", "LHF/ngsc9r>D%68", "LHF/ngsc9r>D%68", "tsuruta@mac.com"));
        userService.saveNewUser(new UserRegistrationDTO("Javan Valdez", "tWkD>5E@6F4an$,", "tWkD>5E@6F4an$,", "lamky@sbcglobal.net"));
        userService.saveNewUser(new UserRegistrationDTO("Daanish Camacho", "Rr5;)QB8qS:3Z}7", "Rr5;)QB8qS:3Z}7", "ivoibs@aol.com"));
        userService.saveNewUser(new UserRegistrationDTO("Shahid Norman", "Rg2H(FtX6@+]r8%", "Rg2H(FtX6@+]r8%", "aukjan@yahoo.ca"));
        userService.saveNewUser(new UserRegistrationDTO("Tea Lucas", "UGXt*-b3_n~Ws5%", "UGXt*-b3_n~Ws5%", "hstiles@sbcglobal.net"));
        userService.saveNewUser(new UserRegistrationDTO("Kinga Noel", "H~}q9P6vc)SC<TM", "H~}q9P6vc)SC<TM", "dsugal@verizon.net"));
        userService.saveNewUser(new UserRegistrationDTO("Tania Bryant", "N{wb>^ky2}VsZ_L", "N{wb>^ky2}VsZ_L", "matty@comcast.net"));
        userService.saveNewUser(new UserRegistrationDTO("Noel Valenzuela", "Z6ukdY#EG[HaBVf", "Z6ukdY#EG[HaBVf", "dsowsy@outlook.com"));
        userService.saveNewUser(new UserRegistrationDTO("Nolan Kline", "F[yM%fUX9^qux+},)H.cTK!(t]eps~", "F[yM%fUX9^qux+},)H.cTK!(t]eps~", "birddog@att.net"));
        userService.saveNewUser(new UserRegistrationDTO("Riley-Jay Handley", "s;~A7jLdUX9?p^/&%Fzf_Hn@[YD}C8", "s;~A7jLdUX9?p^/&%Fzf_Hn@[YD}C8", "kspiteri@hotmail.com"));
        userService.saveNewUser(new UserRegistrationDTO("Braxton Matthews", "xG&[Nm@/_JqDTzc$9d{hb#}7M,;^]+", "xG&[Nm@/_JqDTzc$9d{hb#}7M,;^]+", "horrocks@comcast.net"));
        userService.saveNewUser(new UserRegistrationDTO("Colleen Castro", "LG-j3^m9qRuc;{X=tUw?/ayMk),C~5", "LG-j3^m9qRuc;{X=tUw?/ayMk),C~5", "mjewell@msn.com"));
        userService.saveNewUser(new UserRegistrationDTO("Aine Drake", "uTp@xjzQkEcnVsAUv>(;5aMeG.J]+_", "uTp@xjzQkEcnVsAUv>(;5aMeG.J]+_", "ilovelife55@aol.com"));
        userService.saveNewUser(new UserRegistrationDTO("India Zhang", "H!hdk5r~(A=+GuS<7FbnV#Yc{sPZ2>", "H!hdk5r~(A=+GuS<7FbnV#Yc{sPZ2>", "metzzo@me.com"));
        userService.saveNewUser(new UserRegistrationDTO("Avni Holman", "LJ}s;gKGX-)S2@]#m?z3nCB!7b+jqD", "LJ}s;gKGX-)S2@]#m?z3nCB!7b+jqD", "wayward@att.net"));
        userService.saveNewUser(new UserRegistrationDTO("Fionnuala Burt", "fFEendJ,6XQc}xNU>9;k#m!wY~7%@R", "fFEendJ,6XQc}xNU>9;k#m!wY~7%@R", "geoffr@sbcglobal.net"));
        userService.saveNewUser(new UserRegistrationDTO("Lennox Mendez", "QsUTv?<9E;GD*H{Afr-qX(B.uzZM4j", "QsUTv?<9E;GD*H{Afr-qX(B.uzZM4j", "martyloo@msn.com"));
        userService.saveNewUser(new UserRegistrationDTO("Ralphy Meza", "xTkutzd];Hya$c.9fB2{:SM~ANpr)}", "xTkutzd];Hya$c.9fB2{:SM~ANpr)}", "yangyan@optonline.net"));
    }

    private void seedCircles() {
        circleService.saveCircle(new CircleDTO("The Caring Hand"));
        circleService.saveCircle(new CircleDTO("Happy At Home"));
        circleService.saveCircle(new CircleDTO("House And Home"));
        circleService.saveCircle(new CircleDTO("Precise Care"));
        circleService.saveCircle(new CircleDTO("Sunrise Home Care"));
        circleService.saveCircle(new CircleDTO("Helping Hands At Home"));
        circleService.saveCircle(new CircleDTO("Custom Home Care Solutions"));
        circleService.saveCircle(new CircleDTO("Senior Serenity"));
        circleService.saveCircle(new CircleDTO("Ease Of Effort"));
        circleService.saveCircle(new CircleDTO("Slick Home Care"));
    }

    private void seedMembers() {
        List<UserDTO> allUsers = userService.findAllUsers();
        List<CircleDTO> allCircles = circleService.findAllCircles();
        List<UserDTO> mainUsers = allUsers.subList(0, 10);
        List<UserDTO> extraUsers = allUsers.subList(10, allUsers.size() - 1);

        int i = 0;
        for (UserDTO user : mainUsers) {
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), allCircles.get(i).getCircleId(), "Caregiver", true));
            i = increment(allCircles, i);
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), allCircles.get(i).getCircleId(), "Caregiver", false));
            i = increment(allCircles, i);
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), allCircles.get(i).getCircleId(), "Caregiver", false));
        }

        i = 0;
        for (UserDTO user : extraUsers) {
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), allCircles.get(i).getCircleId(), "Caregiver", true));
            i = increment(allCircles, i);
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), allCircles.get(i).getCircleId(), "Client", false));
            i = increment(allCircles, i);
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), allCircles.get(i).getCircleId(), "Caregiver", false));
            i = increment(allCircles, i);
        }
    }

    private int increment(List<CircleDTO> allCircles, int i) {
        if (i == allCircles.size() - 1) {
            i = 0;
        } else {
            i++;
        }
        return i;
    }

    private void seedTaskLists() {
        List<CircleDTO> allCircles = circleService.findAllCircles();
        for (CircleDTO circle : allCircles) {
            taskListService.saveNew(circle);
        }
    }

    private void seedTasks() {
        List<TaskListDTO> allTaskLists = taskListService.findAllTaskLists();

        for (TaskListDTO taskList : allTaskLists) {
            taskService.save(new TaskDTO("high", "Grocery shopping", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("medium", "Vacuum the living room", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("low", "Walk the dog", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("low", "Change bed linens", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("medium", "Wash the dishes", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("low", "Give daily medicine", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("high", "Get medicine from pharmacy", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("low", "Check blood pressure", taskList.getTaskListId()), taskList.getTaskListId());
            taskService.save(new TaskDTO("medium", "Prepare dinner", taskList.getTaskListId()), taskList.getTaskListId());
        }

    }
}
