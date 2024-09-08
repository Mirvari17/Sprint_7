package scooter.rests;

import io.restassured.response.ValidatableResponse;
import scooter.jsons.Courier;
import scooter.jsons.CourierLogin;

import static scooter.Constants.COURIER_LOGIN_PATH;
import static scooter.Constants.CREATE_COURIER_PATH;
import static scooter.rests.RestBase.spec;

public class CourierRests {
    public static ValidatableResponse createCourierRest(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(CREATE_COURIER_PATH)
                .then().log().all();
    }

    public static ValidatableResponse courierLoginRest(CourierLogin courierLogin) {
        return spec()
                .body(courierLogin)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then().log().all();
    }

    public static ValidatableResponse deleteCourierRest(int id) {
        String deleteCourier = "{\"id\":\"" + id + "\"}";
        return spec()
                .body(deleteCourier)
                .when()
                .delete(CREATE_COURIER_PATH + "/" + id)
                .then().log().all();
    }
}
