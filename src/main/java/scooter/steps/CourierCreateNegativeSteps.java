package scooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import scooter.jsons.Courier;

import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static scooter.rests.CourierRests.createCourierRest;
import static scooter.steps.CourierSteps.courier;

public class CourierCreateNegativeSteps {

    public static int id;

    @Step("Попытка создать второго курьера с такими же параметрами, как у первого")
    public static ValidatableResponse tryCreateSecondCourier() {
        Courier same = courier;  // второй курьер полностью копирует первого
        return createCourierRest(same);
    }

    @Step("Попытка создать курьера без логина")
    public static ValidatableResponse tryCreateCourierWithoutLogin() {
        return createCourierRest(Courier.noLogin());
    }

    @Step("Попытка создать курьера без пароля")
    public static ValidatableResponse tryCreateCourierWithoutPassword() {
        return createCourierRest(Courier.noPassword());
    }

    @Step("Попытка создания курьера без имени")
    public static ValidatableResponse tryCreateCourierWithoutName() {
        return createCourierRest(Courier.noName());
    }

    @Step("Попытка создать второго курьера, с таким же логином")
    public static ValidatableResponse tryCreateCourierWithSameLogin() {
        // генерация json второго курьера, с таким же логином, как у первого, но другим именем
        Courier sameCourierDiffName = new Courier(
                courier.getLogin(),
                courier.getPassword(),
                Courier.random().getFirstName()
        );
        // проверяем, что не удастся создать и вытаскиваем текст ошибки
        ValidatableResponse response = createCourierRest(sameCourierDiffName)
                .assertThat().statusCode(HTTP_CONFLICT);
        return response;
    }
}