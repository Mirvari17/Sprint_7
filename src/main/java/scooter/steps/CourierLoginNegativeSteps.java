package scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import scooter.jsons.CourierLogin;

import static scooter.rests.CourierRests.courierLoginRest;
import static scooter.steps.CourierSteps.courier;

public class CourierLoginNegativeSteps {


    @Step("Попытка логина без пароля")
    public static ValidatableResponse tryLoginWithoutPassword() {
        // создаём json для логина
        CourierLogin c = CourierLogin.noPassword(courier);
        // дёргаем ручку
        return courierLoginRest(c);
    }

    @Step("Попытка создания курьера без логина")
    public static ValidatableResponse tryLoginNoLogin() {
        CourierLogin c = CourierLogin.noLogin(courier);
        return courierLoginRest(c);
    }

    @Step("Попытка логина с некорректным логином")
    public static ValidatableResponse tryLoginWithWrongLogin() {
        CourierLogin c = CourierLogin.wrongLogin(courier);
        return courierLoginRest(c);
    }

    @Step("Логин с некорректным паролем")
    public static ValidatableResponse tryLoginWithWrongPassword() {
        // создание json для такого же логина, но c некорректным паролем
        CourierLogin c = CourierLogin.wrongPassword(courier);
        // дёргаем ручку
        return courierLoginRest(c);
    }
}
