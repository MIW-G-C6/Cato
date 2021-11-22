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
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Tests the about page user interface of the WebApp
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AboutPageUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverFactory webDriverFactory = new WebDriverFactory();
        driver = webDriverFactory.getWebDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void toAboutPageTest() {
        WebElement aboutPageLink = driver.findElement(By.linkText("About"));
        aboutPageLink.click();

        assertEquals("About", driver.getTitle());
        assertEquals("About us", driver.findElement(By.className("aboutHeader")).getText());
    }
}
