package tests.model;

import java.util.ArrayList;
import java.util.List;

public class FactoryLoginPage {
    public static List<LoginPage> createListLoginPage(){
        List<LoginPage> loginPageList = new ArrayList<>();
        loginPageList.add(new LoginPage("t.testovyj@inbox.ru", "123456qwerty123", "Некорректный пароль"));
        loginPageList.add(new LoginPage("t.testovyj@inbo", "123456qwerty", "Некорректный логин"));
        loginPageList.add(new LoginPage("t.testovyj@inbo", "123456qwer", "Некорректный логин и некорректный пароль"));
        loginPageList.add(new LoginPage("T.TESTOVYJ@INBOX.ru", "123456qwerty", "Корректный логин с буквами верхнего регистра"));
        loginPageList.add(new LoginPage("t.testovyj@inbox.ru", "123456QWERTY", "Корректный пароль с буквами верхнего регистра"));
        loginPageList.add(new LoginPage("t.testovyj@inbox.ru", "   123456qwerty", "Некорректный пароль с пробелами в начале"));
        loginPageList.add(new LoginPage("t.testovyj@inbox.ru", "123456qwerty   ", "Некорректный пароль с пробелами в конце"));
        loginPageList.add(new LoginPage("  t.testovyj@inbox.ru", "123456qwerty", "Некорректный логин с пробелами в начале"));
        loginPageList.add(new LoginPage("t.testovyj@inbox.ru   ", "123456qwerty", "Некорректный логин с пробелами в конце"));
        return loginPageList;
    }
}
