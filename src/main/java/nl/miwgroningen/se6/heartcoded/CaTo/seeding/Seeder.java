package nl.miwgroningen.se6.heartcoded.CaTo.seeding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Test data for the database.
 */

@Component
public class Seeder {

    @Autowired
    public Seeder() {
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {

    }

    // method seedUsers()
    // method seedGroups()
    // method seedGroupHasUsers()
    // method seedTask
    // method seedTaskList
}
