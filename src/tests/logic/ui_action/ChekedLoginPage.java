package tests.logic.ui_action;

import org.junit.Assert;
import tests.model.LoginPage;
import utilities.Config.StaticParameters;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ChekedLoginPage {

    public static void fillField(LoginPage page){
        System.out.println("> > Начал работу метод заполнения полей");
        System.out.println("* Заолняю поле Логин");
        $(byName("email")).setValue(page.getLogin());
        System.out.println("* Заполняю поле Пароль");
        $(byName("password")).setValue(page.getPassword());
    }

    public static void clearField(){
        System.out.println("> > Начал работу метод очистки полей");
        System.out.println("* Очищаю поле Логин");
        $(byName("email")).clear();
        System.out.println("* Очищаю поле Пароль");
        $(byName("password")).clear();
    }

    public static void checkIncorrectValueField(String status){
        System.out.println("> > Начал работу метод проверки заполнения некорректных данных");
        if($(byClassName("alert")).exists() || $(byClassName("help-block")).exists()){
            System.out.println("* Проверка некорректных данных: "+ status + " успешна");
        }else{
            Assert.fail("Нет предупреждений о вводе некорректных данных: " + status);
        }
    }

    public static void clickSubmitBtn(){
        System.out.println("* Жму на кнопку Войти");
        $$(byClassName("btn")).findBy(text("Войти")).click();
    }

    public static void checkLogin(){
        System.out.println("> > Проверяю что пользователь вошел на главную страницу");
        if($(byClassName("Exit")).exists()){
            System.out.println("Пользователь успешно вошел на главную страницу");
        }else{
            Assert.fail("Не удалось войти под логином " + StaticParameters.login);
        }
    }

}
