package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Tests the circle dashboard page user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CircleDashboardUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();

        driver.findElement(By.id("username")).sendKeys("klaas@example.com");
        driver.findElement(By.id("password")).sendKeys("a");
        driver.findElement(By.id("login-submit")).click();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void toCareCircleDashboardTest() {
        WebElement careCircleLink = driver.findElement(By.linkText("Sunrise Home Care"));
        careCircleLink.click();

        String expectedUrl = "http://localhost:8080/circles/46";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Circle dashboard", driver.getTitle());
    }

    @Test
    void addNewTaskTest() {
        driver.get("http://localhost:8080/circles/46");
        WebElement addNewTaskLink = driver.findElement(By.linkText("Add new task"));
        addNewTaskLink.click();

        String expectedUrl = "http://localhost:8080/circles/46/taskLists/56/new";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Add task", driver.getTitle());

        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("Do the dishes");

        WebElement startTime = driver.findElement(By.id("startTimeInput"));
        startTime.sendKeys("1112002021t1234");

        WebElement endTime = driver.findElement(By.id("endTimeInput"));
        endTime.sendKeys("12120020211854");

        WebElement saveTask = driver.findElement(By.id("saveNewTask"));
        saveTask.click();

        assertEquals("Circle dashboard", driver.getTitle());
        assertTrue(driver.findElement(By.linkText("Do the dishes")).isDisplayed());
    }

    @Test
    void goToCircleSettingsTest() {
        driver.get("http://localhost:8080/circles/46");

        WebElement settingsLink = driver.findElement(By.linkText("Circle settings"));
        settingsLink.click();

        String expectedUrl = "http://localhost:8080/circles/options/46";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Circle settings", driver.getTitle());
    }
}
