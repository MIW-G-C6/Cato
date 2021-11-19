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
 * Tests the circle overview page user interface of the WebApp.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CircleOverviewUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        if(SystemUtils.IS_OS_WINDOWS){
            System.out.println("Selecting Windows Chrome driver");
            System.setProperty("webdriver.chrome.driver", "chromedrivers\\chromedriver.exe");
        }
        else if(SystemUtils.IS_OS_LINUX){
            System.out.println("Selecting Linux Chrome driver");
            System.setProperty("webdriver.chrome.driver", "chromedrivers\\chromedriver");
        }else{
            throw new UnsupportedOperationException("Operating system not supported by available Chrome web drivers");
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");

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
        WebElement careCircleLink = driver.findElement(By.linkText("Sunrise Home Care"));
        careCircleLink.click();

        String expectedUrl = "http://localhost:8080/circles/46";
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
