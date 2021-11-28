import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest { //эндпойнт api/v1/orders

    @Epic(value = "API Самоката")
    @Feature(value = "Заказ")
    @Story(value = "Список заказов")
    @Test //Проверка условия "Проверь, что в тело ответа возвращается список заказов."
    @DisplayName("Получение списка десяти доступных заказов.")
    @Description("Тест корректности ответа при получении списка десяти доступных заказов для эндпойнта /api/v1/orders.")
    @Owner(value = "Кидяев Александр Дмитриевич")
    @Severity(value = SeverityLevel.NORMAL)
    public void checkResponseForGetTenAvailableOrdersTest () {
        OrderMethods orderList = new OrderMethods();
        Response response = orderList.getTenAvailableOrders();
        response.then().assertThat().body("orders", notNullValue()).and().statusCode(SC_OK);
        //можно было бы сделать (тут и в остальных тестах) сразу orderList.getTenAvailableOrders().then().assertThat() и т.д. но так показалось наглядней
    }
}
