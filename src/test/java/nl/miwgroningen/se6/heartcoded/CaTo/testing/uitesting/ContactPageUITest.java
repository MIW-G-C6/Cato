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
 * Tests the contact page user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContactPageUITest {

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
    void toContactPageTest() {
        WebElement contactPageLink = driver.findElement(By.linkText("Contact"));
        contactPageLink.click();

        assertEquals("Contact us", driver.getTitle());
        assertEquals("Contact us", driver.findElement(By.xpath("//h1")).getText());
    }

}
