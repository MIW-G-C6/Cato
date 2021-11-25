package nl.miwgroningen.se6.heartcoded.CaTo.seeding;

import nl.miwgroningen.se6.heartcoded.CaTo.dto.*;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.UserMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.mappers.UserRegistrationMapper;
import nl.miwgroningen.se6.heartcoded.CaTo.model.User;
import nl.miwgroningen.se6.heartcoded.CaTo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    private TaskLogService taskLogService;

    private UserRegistrationMapper userRegistrationMapper;

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
            System.out.println("Seeding users");
            seedUser();
        }

        if (!userService.emailInUse("admin@admin.com")) {
            System.out.println("Seeding admin");
            userService.saveAdmin();
        }

        if (circleService.findAllCircles().size() == 0) {
            System.out.println("Seeding circles");
            seedCircles();
            if (taskListService.findAllTaskLists().size() == 0) {
                System.out.println("Seeding tasklists");
                seedTaskLists();
            }
        }

        if (memberService.findAllMembers().size() == 0) {
            System.out.println("Seeding members");
            seedMembers();
        }

        if (taskService.findAllTasks().size() == 0) {
            System.out.println("Seeding tasks");
            seedTasks();
        }

    }

    private void seedUser() {

        userService.saveSeederUser(new User("Piet Bakker", "a", "piet@example.com"), "man_1.jpg");
        userService.saveSeederUser(new User("Henk Janssen", "a", "henk@example.com"), "man_2.jpg");
        userService.saveSeederUser(new User("Klaas Smit", "a", "klaas@example.com"), "man_3.jpg");
        userService.saveSeederUser(new User("Willem Visser", "a", "willem@example.com"), "man_4.jpg");
        userService.saveSeederUser(new User("Noah van den Berg", "a", "noah@example.com"), "");
        userService.saveSeederUser(new User("Bram Snor", "a", "bram@example.com"), "man_6.jpg");
        userService.saveSeederUser(new User("Geert Keijzer", "a", "geert@example.com"), "man_7.jpg");
        userService.saveSeederUser(new User("Aart Lutjes", "a", "aart@example.com"), "man_8.jpg");
        userService.saveSeederUser(new User("Steven van der Gracht", "a", "steven@example.com"), "");
        userService.saveSeederUser(new User("Niels Wolkers", "a", "niels@example.com"), "man_10.jpg");
        userService.saveSeederUser(new User("Wouter Geestra", "a", "wouter@example.com"), "man_11.jpg");

        userService.saveSeederUser(new User("Harry Carroll", "h3;#DvVwr[", "keijser@comcast.net"), "man_12.jpg");
        userService.saveSeederUser(new User("Jaydn Wiggins", "H/V86P7s=v", "eimear@outlook.com"), "woman_1.jpg");
        userService.saveSeederUser(new User("Farzana Meadows", "fmRL<3*9%^", "report@live.com"), "woman_2.jpg");
        userService.saveSeederUser(new User("Kareem Dalton", "H[jK!kR65m", "kiddailey@yahoo.com"), "man_13.jpg");
        userService.saveSeederUser(new User("Nasia Barton", "rqf2!P.L8v", "sravani@gmail.com"), "");
        userService.saveSeederUser(new User("Mea Irwin", "RAv]dt;Q9*", "gavinls@yahoo.com"), "woman_4.jpg");
        userService.saveSeederUser(new User("Janae Dillon", "f=Xu/#9?Ze", "malvar@icloud.com"), "woman_5.jpg");
        userService.saveSeederUser(new User("Ayden Adkins", "Xh4H)]eYcq", "peterhoeg@live.com"), "");
        userService.saveSeederUser(new User("Anabelle Hickman", "P6;H7Q%xz-", "arandal@optonline.net"), "woman_6.jpg");
        userService.saveSeederUser(new User("Rhona Bradshaw", "C2t(VE4^kK", "raides@gmail.com"), "woman_7.jpg");
        userService.saveSeederUser(new User("Olivier Clay", "y9W>4grj]L%6!Je", "rkobes@verizon.net"), "");
        userService.saveSeederUser(new User("Wilma Rasmussen", "v,:]PMc4eGS{gx9","bockelboy@hotmail.com"), "woman_8.jpg");
        userService.saveSeederUser(new User("Cathy Mcgee", "LHF/ngsc9r>D%68", "tsuruta@mac.com"), "woman_9.jpg");
        userService.saveSeederUser(new User("Javan Valdez", "tWkD>5E@6F4an$,",  "lamky@sbcglobal.net"), "man_14.jpg");
        userService.saveSeederUser(new User("Daanish Camacho", "Rr5;)QB8qS:3Z}7", "ivoibs@aol.com"), "man_15.jpg");
        userService.saveSeederUser(new User("Shahid Norman", "Rg2H(FtX6@+]r8%", "aukjan@yahoo.ca"), "man_16.jpg");
        userService.saveSeederUser(new User("Tea Lucas", "UGXt*-b3_n~Ws5%", "hstiles@sbcglobal.net"), "woman_10.jpg");
        userService.saveSeederUser(new User("Kinga Noel", "H~}q9P6vc)SC<TM", "dsugal@verizon.net"), "woman_11.jpg");
        userService.saveSeederUser(new User("Tania Bryant", "N{wb>^ky2}VsZ_L", "matty@comcast.net"), "woman_12.jpg");
        userService.saveSeederUser(new User("Noel Valenzuela", "Z6ukdY#EG[HaBVf", "dsowsy@outlook.com"), "man_17.jpg");
        userService.saveSeederUser(new User("Nolan Kline", "F[yM%fUX9^qux+},)H.cTK!(t]eps~", "birddog@att.net"), "");
        userService.saveSeederUser(new User("Riley-Jay Handley", "s;~A7jLdUX9?p^/&%Fzf_Hn@[YD}C8", "kspiteri@hotmail.com"), "woman_13.jpg");
        userService.saveSeederUser(new User("Braxton Matthews", "xG&[Nm@/_JqDTzc$9d{hb#}7M,;^]+",  "horrocks@comcast.net"), "man_5.jpg");
        userService.saveSeederUser(new User("Colleen Castro", "LG-j3^m9qRuc;{X=tUw?/ayMk),C~5", "mjewell@msn.com"), "woman_14.jpg");
        userService.saveSeederUser(new User("Aine Drake", "uTp@xjzQkEcnVsAUv>(;5aMeG.J]+_", "ilovelife55@aol.com"), "");
        userService.saveSeederUser(new User("India Zhang", "H!hdk5r~(A=+GuS<7FbnV#Yc{sPZ2>", "metzzo@me.com"), "woman_16.jpg");
        userService.saveSeederUser(new User("Avni Holman", "LJ}s;gKGX-)S2@]#m?z3nCB!7b+jqD", "wayward@att.net"), "woman_15.jpg");
        userService.saveSeederUser(new User("Fionnuala Burt", "fFEendJ,6XQc}xNU>9;k#m!wY~7%@R", "geoffr@sbcglobal.net"), "woman_17.jpg");
        userService.saveSeederUser(new User("Lennox Mendez", "QsUTv?<9E;GD*H{Afr-qX(B.uzZM4j", "martyloo@msn.com"), "man_18.jpg");
        userService.saveSeederUser(new User("Ralphy Meza", "xTkutzd];Hya$c.9fB2{:SM~ANpr)}", "yangyan@optonline.net"), "man_19.jpg");
    }

    private void seedCircles() {
        circleService.saveCircle(new CircleDTO("The Caring Hand"));
        circleService.saveCircle(new CircleDTO("Happy At Home"));
//        circleService.saveCircle(new CircleDTO("House And Home"));
        circleService.saveCircle(new CircleDTO("Precise Care"));
        circleService.saveCircle(new CircleDTO("Sunrise Home Care"));
//        circleService.saveCircle(new CircleDTO("Helping Hands At Home"));
        circleService.saveCircle(new CircleDTO("Custom Home Care Solutions"));
        circleService.saveCircle(new CircleDTO("Senior Serenity"));
//        circleService.saveCircle(new CircleDTO("Ease Of Effort"));
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
            if (i % 3 == 0) {
                memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), allCircles.get(i).getCircleId(), "Caregiver", false));
                i = increment(allCircles, i);
            }
        }

        i = 0;
        for (CircleDTO circle : allCircles) {
            UserDTO user = mainUsers.get(i);
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), circle.getCircleId(), "Client", true));
            i = increment(allCircles, i);
        }

        i = 0;
        while (extraUsers.size() > 0) {
            CircleDTO circle = allCircles.get(i);
            UserDTO user = extraUsers.get(0);
            memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), circle.getCircleId(), "Caregiver", false));
            i = increment(allCircles, i);

            if (allUsers.size()%3 == 0) {
                circle = allCircles.get(i);
                memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), circle.getCircleId(), "Caregiver", false));
                i = increment(allCircles, i);
            }

            if (allUsers.size()%5 == 0) {
                circle = allCircles.get(i);
                memberService.saveMember(new MemberDTO(user.getUserId(), user.getName(), circle.getCircleId(), "Client", false));
                i = increment(allCircles, i);
            }
            extraUsers.remove(0);
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

        Integer adminId = userService.getAdminId("ROLE_ADMIN");

        for (TaskListDTO taskList : allTaskLists) {
            taskService.save(new TaskDTO("High", "Grocery shopping", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("Medium", "Vacuum the living room", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("Low", "Walk the dog", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("Low", "Change bed linens", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("Medium", "Wash the dishes", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("Low", "Give daily medicine", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("High", "Get medicine from pharmacy", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("Low", "Check blood pressure", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
            taskService.save(new TaskDTO("Medium", "Prepare dinner", taskList.getTaskListId()), taskList.getTaskListId(), adminId);
        }
    }
}
