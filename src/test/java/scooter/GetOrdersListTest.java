package scooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static scooter.steps.OrderSteps.checkOrdersNotNull;
import static scooter.steps.OrderSteps.getOrdersList;

public class GetOrdersListTest {

    @Test
    @DisplayName("[+] Orders - Получение списка заказов")
    public void getOrdersListTest() {
        getOrdersList();
        checkOrdersNotNull();
    }
}