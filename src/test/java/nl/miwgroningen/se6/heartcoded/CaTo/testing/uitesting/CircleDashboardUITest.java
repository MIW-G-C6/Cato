package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
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
        WebElement careCircleLink = driver.findElement(By.linkText("Precise Care"));
        careCircleLink.click();

        String expectedUrl = "http://localhost:8080/circles/45";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Circle dashboard", driver.getTitle());
    }

    @Test
    void addNewTaskTest() {
        driver.get("http://localhost:8080/circles/45");
        WebElement addNewTaskLink = driver.findElement(By.linkText("Add new task"));
        addNewTaskLink.click();

        String expectedUrl = "http://localhost:8080/circles/45/taskLists/52/new";
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
    }

    @Test
    void editTaskTest() {
        driver.get("http://localhost:8080/circles/45");

        WebElement taskLink = driver.findElement(By.linkText("Grocery shopping"));
        taskLink.click();

        String expectedUrl = "http://localhost:8080/circles/45/taskLists/52/93";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Task Details", driver.getTitle());

        WebElement taskEditLink = driver.findElement(By.linkText("Edit task"));
        taskEditLink.click();

        expectedUrl = "http://localhost:8080/circles/45/taskLists/52/update/93";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Edit task", driver.getTitle());

        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, "Grocery shopping, don't forget the milk");

        WebElement mediumPriorityRadio = driver.findElement(By.id("priority2"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true", mediumPriorityRadio);

        WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
        saveButton.click();

        assertEquals("Circle dashboard", driver.getTitle());
        assertTrue(driver.findElement(By.linkText("Grocery shopping, don't forget the milk")).isDisplayed());

        taskLink = driver.findElement(By.linkText("Grocery shopping, don't forget the milk"));
        taskLink.click();

        assertEquals("Medium", driver.findElement(By.id("priority")).getText());
    }

    @Test
    void goToCircleSettingsTest() {
        driver.get("http://localhost:8080/circles/45");

        WebElement settingsLink = driver.findElement(By.linkText("Circle settings"));
        settingsLink.click();

        String expectedUrl = "http://localhost:8080/circles/options/45";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Circle settings", driver.getTitle());
    }
}
