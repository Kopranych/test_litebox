package tests.init;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.logic.ui_action.ChekedLoginPage;
import tests.model.FactoryLoginPage;
import tests.model.LoginPage;
import utilities.Config.DriverTools;
import utilities.Config.StaticParameters;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static tests.model.FactoryLoginPage.createListLoginPage;
import static utilities.Config.StaticParameters.excludeVisuals;

public class Login {
    public static WebDriver driver;
    static String url;

    @Parameters({"baseUrl", "holdBrowser", "browser", "reportsFolder", "timeout", "login", "password", "excludeVisuals", "delay"})
    @BeforeSuite
    public static void setUp(String baseUrl, String holdBrowser, String browser, String reportsFolder, String timeout, String login, String password, boolean excludeVisuals, int delay) throws Exception {
        StaticParameters.baseUrl = baseUrl;
        StaticParameters.login = login;
        StaticParameters.password = password;
        StaticParameters.excludeVisuals = excludeVisuals;
        StaticParameters.delay = delay;
        StaticParameters.timeout = Integer.parseInt(timeout);

        if (!excludeVisuals) {
            Configuration.holdBrowserOpen = holdBrowser.contentEquals("true") ? true : false;
            Configuration.timeout = Long.parseLong(timeout);
            Configuration.pageLoadStrategy = "normal";
            Configuration.savePageSource = false;
            if (!reportsFolder.isEmpty())
                Configuration.reportsFolder = reportsFolder;

            driver = DriverTools.createDriver(browser);
            driver.manage().window().maximize();

            WebDriverRunner.setWebDriver(driver);

            url = StaticParameters.baseUrl;

            open(url);
        }
    }

    @Parameters({"holdBrowser"})
    @AfterSuite(description = "Закрываем драйвер")
    public static void tearDown(String holdBrowser) throws Exception {
        if (!excludeVisuals) {
            if (!holdBrowser.contentEquals("true") ? true : false)
                driver.quit();
        }

    }

    @Test(description = "Логинимся на сайт")
    public void Login() {
        LoginPage page = new LoginPage(StaticParameters.login, StaticParameters.password, "Корректный логин и пароль");
        ChekedLoginPage.fillField(page);
        ChekedLoginPage.clickSubmitBtn();
        ChekedLoginPage.checkLogin();
    }

    @Test(description = "Проверка ввода некорректных данных")
    public void incorrectLogin() {
        List<LoginPage> loginPageList = FactoryLoginPage.createListLoginPage();
        loginPageList.forEach(loginPage -> {
            ChekedLoginPage.fillField(loginPage);
            ChekedLoginPage.clickSubmitBtn();
            ChekedLoginPage.checkIncorrectValueField(loginPage.getStatus());
            ChekedLoginPage.clearField();
        });

    }


}