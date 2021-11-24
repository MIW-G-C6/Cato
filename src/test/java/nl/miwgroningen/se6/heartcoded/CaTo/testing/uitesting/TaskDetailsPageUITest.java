package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Tests the task details user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TaskDetailsPageUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();

        driver.findElement(By.id("username")).sendKeys("piet@example.com");
        driver.findElement(By.id("password")).sendKeys("a");
        driver.findElement(By.id("login-submit")).click();

        driver.get("http://localhost:8080/circles/45/taskLists/55/111");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void editTaskTest() {
        WebElement edit = driver.findElement(By.linkText("Edit task"));
        edit.click();

        String expectedUrl = "http://localhost:8080/circles/45/taskLists/55/update/111";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Edit task", driver.getTitle());

        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys(Keys.chord(Keys.CONTROL, "a"), "Get Medicine");

        WebElement save = driver.findElement(By.xpath("//button[@type='submit']"));
        save.click();

        expectedUrl = "http://localhost:8080/circles/45";
        assertEquals(expectedUrl, driver.getCurrentUrl());

        driver.get("http://localhost:8080/circles/45/taskLists/55/111");
        description = driver.findElement(By.id("description"));
        assertEquals(description.getText(), "Get Medicine");
    }
}
