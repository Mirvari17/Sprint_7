package scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static scooter.steps.OrderSteps.checkOrdersNotNull;
import static scooter.steps.OrderSteps.getOrdersList;

public class GetOrdersListTest {

    @Test
    @DisplayName("[+] Orders - Получение списка заказов")
    public void getOrdersListTest() {
        ValidatableResponse response = getOrdersList();
        checkOrdersNotNull(response);
    }
}