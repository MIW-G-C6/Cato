package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Tests the homepage user interface of the WebApp.
 */

public class HomepageUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void loginTest() {
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys("klaas@example.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("a");

        WebElement login = driver.findElement(By.id("login-submit"));
        login.click();

        String expectedUrl = "http://localhost:8080/circles";

        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    void loginTestFailed() {
        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys("klaas@example.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("b");

        WebElement login = driver.findElement(By.id("login-submit"));
        login.click();

        WebElement error = driver.findElement(By.className("alert"));

        String expectedUrl = "http://localhost:8080/?error";

        assertTrue(error.isDisplayed());
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    void toRegistrationPageTest() {
        WebElement registerHereLink = driver.findElement(By.linkText("Register here"));
        registerHereLink.click();

        String expectedUrl = "http://localhost:8080/registration";

        assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
