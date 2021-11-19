package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.apache.commons.lang.SystemUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Tests the registration page user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegistrationUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        webDriverFactory.getWebDriver();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/registration");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void registrationTest() {
        WebElement username = driver.findElement(By.id("name"));
        username.sendKeys("test_name");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@example.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("test");

        WebElement confirmPassword = driver.findElement(By.id("confirm_password"));
        confirmPassword.sendKeys("test");

        WebElement save = driver.findElement(By.id("save_user"));
        save.click();

        String expectedUrl = "http://localhost:8080/";

        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    void registrationTestFailed() {
        WebElement username = driver.findElement(By.id("name"));
        username.sendKeys("Piet");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("piet@example.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("a");

        WebElement confirmPassword = driver.findElement(By.id("confirm_password"));
        confirmPassword.sendKeys("a");

        WebElement save = driver.findElement(By.id("save_user"));
        save.click();

        WebElement error = driver.findElement(By.className("alert"));

        String expectedUrl = "http://localhost:8080/registration";

        assertTrue(error.isDisplayed());
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
