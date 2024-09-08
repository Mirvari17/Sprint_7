package scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import scooter.jsons.Courier;
import scooter.jsons.CourierLogin;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static scooter.rests.CourierRests.*;

public class CourierSteps {

    public static Courier courier;
    public static int id;
    public static CourierLogin courierLogin;

    @Step("Создание курьера")
    public static ValidatableResponse createCourier() {
        // генерируем рандомного курьера
        courier = Courier.random();
        // дёргаем ручку создания
        return createCourierRest(courier);
    }

    @Step("Проверка статус кода")
    public static void checkStatusCode(ValidatableResponse response, int statusCode) {
        response.assertThat().statusCode(statusCode);
    }

    @Step("Проверка текста сообщения")
    public static void checkTextMessage(ValidatableResponse response, String expectedMessage) {
        assertEquals(expectedMessage, response.extract().path("message"));
    }

    @Step("Логин курьера")
    public static ValidatableResponse loginCourier() {
        // создаём json для логина
        courierLogin = CourierLogin.from(courier);
        // дёргаем ручку логина, чтобы узнать ID, чтобы потом удалить курьера
        return courierLoginRest(courierLogin);
    }

    public static void deleteCourier() {
        if (id > 0) {
            boolean delete = deleteCourierRest(id)
                    .assertThat().statusCode(HTTP_OK)
                    .extract().path("ok");
            assertTrue(delete);
            id = 0;
        }
    }

    @Step("Удаление курьера, если был создан")
    public static void deleteCourierIfExists() {
        if (courier != null) {
            loginCourier();  // чтобы удалить курьера, нам нужно его залогинить, чтобы узнать его id
            deleteCourier();
            courier = null;  // в конце очистить переменную
        }
    }
}