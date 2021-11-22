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
 * Tests the circle settings page user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CircleSettingsUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();

        driver.findElement(By.id("username")).sendKeys("piet@example.com");
        driver.findElement(By.id("password")).sendKeys("a");
        driver.findElement(By.id("login-submit")).click();

        driver.get("http://localhost:8080/circles/options/42");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void addMemberCancelTest() {
        WebElement addCircleMemberLink = driver.findElement(By.linkText("Add circle member"));
        addCircleMemberLink.click();

        String expectedUrl = "http://localhost:8080/circles/options/42/addmember";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Add a Care Circle member", driver.getTitle());

        WebElement cancelButton = driver.findElement(By.xpath("//a[@type='button']"));
        cancelButton.click();

        expectedUrl = "http://localhost:8080/circles/options/42";
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    void goToEditMemberRoleTest() {
        WebElement editRole = driver.findElement(By
                .xpath("//*[@id=\"group-settings-table\"]/table/tbody/tr[2]/td[2]/a/img"));
        editRole.click();

        String expectedUrl = "http://localhost:8080/circles/options/42/updatemember/1";
        assertEquals(expectedUrl, driver.getCurrentUrl());
        assertEquals("Edit member", driver.getTitle());
    }

}
