import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest { //эндпойнт api/v1/orders

    @Test //Проверка условия "Проверь, что в тело ответа возвращается список заказов."
    @DisplayName("Получение списка десяти доступных заказов.")
    @Description("Тест корректности ответа при получении списка десяти доступных заказов для эндпойнта /api/v1/orders.")
    public void checkResponseForGetTenAvailableOrdersTest () {
        OrderMethods orderList = new OrderMethods();
        Response response = orderList.getTenAvailableOrders();
        response.then().assertThat().body("orders", notNullValue()).and().statusCode(SC_OK);
        //можно было бы сделать (тут и в остальных тестах) сразу orderList.getTenAvailableOrders().then().assertThat() и т.д. но так показалось наглядней
    }
}
