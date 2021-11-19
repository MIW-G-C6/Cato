package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Helper class to get the right chrome driver.
 */

public class WebDriverFactory {


    public WebDriver getWebDriver(){
        return getWebDriver("http://localhost:8080/");
    }

    public WebDriver getWebDriver(String url) {

        boolean setHeadless = false;

        if(SystemUtils.IS_OS_WINDOWS){
            System.out.println("Selecting Windows Chrome driver");
            System.setProperty("webdriver.chrome.driver", "chromedrivers\\chromedriver.exe");
        }
        else if(SystemUtils.IS_OS_LINUX){
            setHeadless = true;
            System.out.println("Selecting Linux Chrome driver");
            System.setProperty("webdriver.chrome.driver", "chromedrivers/chromedriver");
        }else{
            throw new UnsupportedOperationException("Operating system not supported by available Chrome web drivers");
        }

        ChromeOptions options = new ChromeOptions();
        if(setHeadless) {
            options.addArguments("--headless");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }
}
