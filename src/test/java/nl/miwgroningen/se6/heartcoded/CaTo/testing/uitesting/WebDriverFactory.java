package nl.miwgroningen.se6.heartcoded.CaTo.testing.uitesting;

import org.apache.commons.lang.SystemUtils;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Helper class to get the right chrome driver.
 */

public class WebDriverFactory {

    public void getWebDriver() {
        if(SystemUtils.IS_OS_WINDOWS){
            System.out.println("Selecting Windows Chrome driver");
            System.setProperty("webdriver.chrome.driver", "chromedrivers\\chromedriver.exe");
        }
        else if(SystemUtils.IS_OS_LINUX){
            System.out.println("Selecting Linux Chrome driver");
            System.setProperty("webdriver.chrome.driver", "chromedrivers/chromedriver");
        }else{
            throw new UnsupportedOperationException("Operating system not supported by available Chrome web drivers");
        }
    }
}
