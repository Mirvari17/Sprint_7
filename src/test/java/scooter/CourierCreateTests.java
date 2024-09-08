package scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static scooter.Constants.LOGIN_ALREADY_USED;
import static scooter.Constants.NOT_ENOUGH_DATA_TO_CREATE;
import static scooter.steps.CourierCreateNegativeSteps.*;
import static scooter.steps.CourierSteps.*;

public class CourierCreateTests {

    @After
    public void deleteCourier() {
        deleteCourierIfExists();
    }

    @Test
    @DisplayName("[+] Courier - Создание курьера")
    public void createCourierTest() {
        ValidatableResponse response = createCourier();
        checkStatusCode(response, HTTP_CREATED);
    }

    // Этот тест упадёт, т.к. текст ответа не соответствует документации
    @Test
    @DisplayName("[-] Courier - Создание курьера: два одинаковых")
    public void tryCreateTwoSameCouriersTest() {
        ValidatableResponse response;
        createCourier();
        response = tryCreateSecondCourier();
        checkStatusCode(response, HTTP_CONFLICT);
        checkTextMessage(response, LOGIN_ALREADY_USED);
    }

    @Test
    @DisplayName("[-] Courier - Создание курьера: без логина")
    public void tryCreateCourierWithoutLoginTest() {
        ValidatableResponse response = tryCreateCourierWithoutLogin();
        checkStatusCode(response, HTTP_BAD_REQUEST);
        checkTextMessage(response, NOT_ENOUGH_DATA_TO_CREATE);
    }

    @Test
    @DisplayName("[-] Courier - Создание курьера: без пароля")
    public void tryCreateCourierWithoutPasswordTest() {
        ValidatableResponse response = tryCreateCourierWithoutPassword();
        checkStatusCode(response, HTTP_BAD_REQUEST);
        checkTextMessage(response, NOT_ENOUGH_DATA_TO_CREATE);
    }

    // этот тест упадёт, т.к. система позволяет создать курьера без имени
    @Test
    @DisplayName("[-] Courier - Создание курьера: без имени")
    public void tryCreateCourierWithoutNameTest() {
        ValidatableResponse response = tryCreateCourierWithoutName();
        checkStatusCode(response, HTTP_BAD_REQUEST);
        checkTextMessage(response, NOT_ENOUGH_DATA_TO_CREATE);
    }

    // этот тест упадёт, т.к. текст ответа не соответствует документации
    @Test
    @DisplayName("[-] Courier - Создание курьера: с существующим логином")
    public void createCourierWithLoginExistsTest() {
        createCourier();
        ValidatableResponse response = tryCreateCourierWithSameLogin();
        checkTextMessage(response, LOGIN_ALREADY_USED);
    }
}