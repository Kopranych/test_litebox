package utilities.Config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.logging.Level;

public class DriverTools {
    public static WebDriver createDriver(String browser) {
        WebDriver driver = null;

        if (browser.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "lib\\chromedriver.exe");
            DesiredCapabilities caps = DesiredCapabilities.chrome();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.BROWSER, Level.ALL);
            caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
            driver = new ChromeDriver(caps);

        } else {
            System.out.println("Был выбран браузер, который в данный момент не поддерживается: <" + browser + ">!");
        }

        return driver;
    }
}
