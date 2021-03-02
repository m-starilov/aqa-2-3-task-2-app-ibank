package ru.netology.web.test;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;

import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataForRegistration.*;

public class AppIBankTest {
    private static final Faker faker = new Faker(new Locale("en"));
    private static String login;
    private static String password;

    @BeforeEach
    void setUp() {
        login = faker.name().username();
        password = faker.internet().password();
        open("http://localhost:9999");
    }

    @Test
    void shouldLogInExistUser() {
        DataGenerator.userSetUp(login, password, active);
        SelenideElement form = $("form");
        form.$("[data-test-id=login] input").setValue(login);
        form.$("[data-test-id=password] input").setValue(password);
        form.$$("button").find(exactText("Продолжить")).click();
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotLogInNonExistUser() {
        SelenideElement form = $("form");
        form.$("[data-test-id=login] input").setValue(login);
        form.$("[data-test-id=password] input").setValue(password);
        form.$$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка")).shouldBe(visible);
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldLogInActiveUser() {
        DataGenerator.userSetUp(login, password, active);
        SelenideElement form = $("form");
        form.$("[data-test-id=login] input").setValue(login);
        form.$("[data-test-id=password] input").setValue(password);
        form.$$("button").find(exactText("Продолжить")).click();
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotLogInBlockedUser() {
        DataGenerator.userSetUp(login, password, blocked);
        SelenideElement form = $("form");
        form.$("[data-test-id=login] input").setValue(login);
        form.$("[data-test-id=password] input").setValue(password);
        form.$$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка")).shouldBe(visible);
        $(withText("Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldNotLogInInvalidLogin() {
        DataGenerator.userSetUp(login, password, active);
        SelenideElement form = $("form");
        form.$("[data-test-id=login] input").setValue(String.join("", login, "1"));
        form.$("[data-test-id=password] input").setValue(password);
        form.$$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка")).shouldBe(visible);
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotLogInInvalidPassword() {
        DataGenerator.userSetUp(login, password, active);
        SelenideElement form = $("form");
        form.$("[data-test-id=login] input").setValue(login);
        form.$("[data-test-id=password] input").setValue(password.toUpperCase());
        form.$$("button").find(exactText("Продолжить")).click();
        $(byText("Ошибка")).shouldBe(visible);
        $(withText("Неверно указан логин или пароль")).shouldBe(visible);
    }
}
