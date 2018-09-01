package tests.init;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tests.logic.ui_action.ChekedLoginPage;
import tests.model.FactoryLoginPage;
import tests.model.LoginPage;
import utilities.Config.DriverTools;
import utilities.Config.StaticParameters;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
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
        System.out.println("[ЛОГИНИМСЯ НА САЙТ]");
        LoginPage page = new LoginPage(StaticParameters.login, StaticParameters.password, "Корректный логин и пароль");
        ChekedLoginPage.LogOut();
        ChekedLoginPage.fillField(page);
        ChekedLoginPage.clickSubmitBtn();
        ChekedLoginPage.checkLogin();
    }

    @Test(description = "Проверка ввода некорректных данных")
    public void incorrectAll() {
        List<LoginPage> loginPageList = FactoryLoginPage.createListLoginPage();
        loginPageList.forEach(loginPage -> {
            ChekedLoginPage.fillField(loginPage);
            ChekedLoginPage.clickSubmitBtn();
            ChekedLoginPage.checkIncorrectValueField(loginPage.getStatus());
            ChekedLoginPage.clearField();
        });
    }

    @Test(description = "Некорректный пароль")
    public void incorrectPassword() {
        System.out.println("[НЕКОРРЕКТНЫЙ ПАРОЛЬ]");
        LoginPage loginPage = new LoginPage("t.testovyj@inbox.ru", "123456qwerty123", "Некорректный пароль");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Некорректный логин")
    public void incorrectLogin() {
        System.out.println("[НЕКОРРЕКТНЫЙ ЛОГИН]");
        LoginPage loginPage = new LoginPage("t.testovyj@inbox.ru", "123456qwerty123", "Некорректный пароль");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Некорректный логин и некорректный пароль")
    public void incorrectLoginAndPassword() {
        System.out.println("[НЕКОРРЕКТНЫЙ ЛОГИН И ПАРОЛЬ]");
        LoginPage loginPage = new LoginPage("t.testovyj@inbo", "123456qwer", "Некорректный логин и некорректный пароль");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Корректный логин с буквами верхнего регистра")
    public void correctLoginUpper() {
        System.out.println("[КОРРЕКТНЫЙ ЛОГИН С БУКВАМ ВЕРЗНЕГО РЕГИСТРА]");
        LoginPage loginPage = new LoginPage("T.TESTOVYJ@INBOX.ru", "123456qwerty", "Корректный логин с буквами верхнего регистра");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Корректный логин с буквами верхнего и нижнего регистра")
    public void correctLoginUpperLower() {
        System.out.println("[КОРРЕКТНЫЙ ЛОГИН С БУКВАМИ ВЕРХНЕГО И НИЖНЕО РЕГИСТРА]");
        LoginPage loginPage = new LoginPage("T.tEsToVyJ@InBoX.ru", "123456qwerty", "Корректный логин с буквами верхнего регистра и нижнего регистра");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Корректный пароль с буквами верхнего регистра")
    public void correctPasswordUpper() {
        System.out.println("[КОРРЕКТНЫЙ ПАРОЛЬ С БУКВАМИ ВЕРХНЕГО РЕГИСТРА]");
        LoginPage loginPage = new LoginPage("t.testovyj@inbox.ru", "123456QWERTY", "Корректный пароль с буквами верхнего регистра");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Корректный пароль с буквами верхнего и нижнего регистра")
    public void correctPasswordUpperLower() {
        System.out.println("[КОРРЕКТНЫЙ ПАРОЛЬ С БУКВАМИ ВЕРХНЕГО И НИЖНЕГО РЕГИСТРА]");
        LoginPage loginPage = new LoginPage("t.testovyj@inbox.ru", "123456QwErTy", "Корректный пароль с буквами верхнего и нижнего регистра");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Некорректный пароль с пробелами в начале")
    public void incorrectPasswordSpaceBegin() {
        System.out.println("[НЕКОРРЕКТНЫЙ ПАРОЛЬ С ПРОБЕЛАМИ В НАЧАЛЕ");
        LoginPage loginPage = new LoginPage("t.testovyj@inbox.ru", "   123456qwerty", "Некорректный пароль с пробелами в начале");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Некорректный пароль с пробелами в конце")
    public void incorrectPasswordSpaceEnd() {
        System.out.println("[НЕКОРРЕКТНЫЙ ПАРОЛЬ С ПРОБЕЛАМИ В КОНЦЕ]");
        LoginPage loginPage = new LoginPage("t.testovyj@inbox.ru", "123456qwerty   ", "Некорректный пароль с пробелами в конце");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Некорректный логин с пробелами в начале")
    public void incorrectLoginSpaceBegin() {
        System.out.println("[НЕКОРРЕКТНЫЙ ПАРОЛЬ С ПРОБЕЛАМИ В НАЧАЛЕ]");
        LoginPage loginPage = new LoginPage("  t.testovyj@inbox.ru", "123456qwerty", "Некорректный логин с пробелами в начале");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }

    @Test(description = "Некорректный логин с пробелами в конце")
    public void incorrectLoginSpaceEnd() {
        System.out.println("[НЕКОРРЕКТНЫЙ ЛОГИН С ПРОБЕЛАМИ В КОНЦЕ]");
        LoginPage loginPage = new LoginPage("t.testovyj@inbox.ru   ", "123456qwerty", "Некорректный логин с пробелами в конце");
        ChekedLoginPage.checkCaseIncorrectData(loginPage);
    }


}