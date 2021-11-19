package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

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
 * Tests the user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserInterfaceTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void showHomePageTest() {
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");
    }

    @Test
    void loginTest() {
        driver.get("http://localhost:8080/");

        WebElement email = driver.findElement(By.id("username"));
        email.sendKeys("klaas@example.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("a");

        WebElement login = driver.findElement(By.id("login-submit"));
        login.click();

        String expectedUrl = "http://localhost:8080/circles";

        assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
