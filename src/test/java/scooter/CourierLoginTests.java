package scooter;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static scooter.Constants.*;
import static scooter.steps.CourierLoginNegativeSteps.*;
import static scooter.steps.CourierSteps.*;

public class CourierLoginTests {

    @Before
    public void createAndLoginCourier() {
        createCourier();
    }

    @After
    public void deleteCourier() {
        deleteCourierIfExists();
        courier = null;
    }

    @Test
    @DisplayName("[+] Courier - Логин курьера в системе")
    public void loginCourierTest() {
        ValidatableResponse response = loginCourier();
        checkStatusCode(response, HTTP_OK);
    }

    // этот тест падает по таймауту (1 мин)
    @Test
    @DisplayName("[-] Courier - Логин курьера в системе: без пароля")
    public void loginWithoutPasswordTest() {
        ValidatableResponse response = tryLoginWithoutPassword();
        checkStatusCode(response, HTTP_UNAUTHORIZED);
        checkTextMessage(response, NOT_ENOUGH_DATA_TO_LOGIN);
    }

    @Test
    @DisplayName("[-] Courier - Логин курьера в системе: без логина")
    public void loginWithoutLoginTest() {
        ValidatableResponse response = tryLoginNoLogin();
        checkStatusCode(response, HTTP_BAD_REQUEST);
        checkTextMessage(response, NOT_ENOUGH_DATA_TO_LOGIN);
    }

    @Test
    @DisplayName("[-] Courier - Логин курьера в системе: некорректный логин")
    public void loginWithWrongLoginTest() {
        ValidatableResponse response = tryLoginWithWrongLogin();
        checkStatusCode(response, HTTP_NOT_FOUND);
        checkTextMessage(response, ACCOUNT_NOT_FOUND);
    }

    @Test
    @DisplayName("[-] Courier - Логин курьера в системе: корректный логин, некорректный пароль")
    public void loginWithWrongPasswordTest() {
        ValidatableResponse response = tryLoginWithWrongPassword();
        checkStatusCode(response, HTTP_NOT_FOUND);
        checkTextMessage(response, ACCOUNT_NOT_FOUND);
    }
}