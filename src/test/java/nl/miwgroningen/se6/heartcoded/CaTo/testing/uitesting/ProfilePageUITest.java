package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Tests the profile page user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProfilePageUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();

        driver.findElement(By.id("username")).sendKeys("piet@example.com");
        driver.findElement(By.id("password")).sendKeys("a");
        driver.findElement(By.id("login-submit")).click();

        driver.get("http://localhost:8080/profilepage/1");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void editNameTest() {
        WebElement edit = driver.findElement(By.id("editPersonalInformation"));
        edit.click();

        String expectedUrl = "http://localhost:8080/users/edit/1";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Edit personal information", driver.getTitle());

        WebElement name = driver.findElement(By.id("name"));
        name.sendKeys(Keys.chord(Keys.CONTROL, "a"), "Pieter Bakker");

        WebElement saveInfo = driver.findElement(By.xpath("//button[@type='submit']"));
        saveInfo.click();

        expectedUrl = "http://localhost:8080/profilepage/1";
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    void changePasswordTest() {
        WebElement changePassword = driver.findElement(By.id("changePassword"));
        changePassword.click();

        String expectedUrl = "http://localhost:8080/users/edit/password/1";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Edit personal information", driver.getTitle());

        WebElement currentPassword = driver.findElement(By.id("currentPassword"));
        currentPassword.sendKeys("a");

        WebElement newPassword = driver.findElement(By.id("newPassword"));
        newPassword.sendKeys("b");

        WebElement confirmNewPassword = driver.findElement(By.id("repeatNewPassword"));
        confirmNewPassword.sendKeys("b");

        WebElement savePassword = driver.findElement(By.xpath("//button[@type='submit']"));
        savePassword.click();

        expectedUrl = "http://localhost:8080/profilepage/1";
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
