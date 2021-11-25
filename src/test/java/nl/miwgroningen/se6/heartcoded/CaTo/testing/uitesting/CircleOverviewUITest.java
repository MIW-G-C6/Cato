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
 * Tests the circle overview page user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CircleOverviewUITest {

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
    void toCareCircleTest() {
        WebElement careCircleLink = driver.findElement(By.linkText("Precise Care"));
        careCircleLink.click();

        String expectedUrl = "http://localhost:8080/circles/45";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Circle dashboard", driver.getTitle());

        WebElement careCircleBreadCrumb = driver.findElement(By.linkText("Care Circles"));
        careCircleBreadCrumb.click();

        assertEquals("My circles", driver.getTitle());
    }

    @Test
    void createNewCircleTest() {
        WebElement newCircleLink = driver.findElement(By.linkText("Create new Care Circle"));
        newCircleLink.click();

        String expectedUrl = "http://localhost:8080/circles/new";
        assertEquals(expectedUrl, driver.getCurrentUrl());

        WebElement circleName = driver.findElement(By.id("circleName"));
        circleName.sendKeys("test_group");

        WebElement saveCircle = driver.findElement(By.id("save_circle"));
        saveCircle.click();

        assertEquals("Circle dashboard", driver.getTitle());
    }
}
