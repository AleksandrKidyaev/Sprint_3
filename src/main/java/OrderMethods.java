import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderMethods extends RestAssuredSpecification {

    @Step("Получение десяти доступных заказов.")
    public Response getTenAvailableOrders() {
        return given()
                .spec(getBaseSpec())
                .get("/api/v1/orders?limit=10&page=0");
    }

    @Step("Создание заказа.")
    public Response createOrderWithSpecificColor (String color) {
        String createOrderBody = "{\"firstName\": \"Alexandr\",\"lastName\": \"Kidyaev\",\"address\": \"Moscow, ul. Lenina, 21\",\"metroStation\": 4,\"phone\": \"+7 915 111 22 33\",\"rentTime\": 6,\"deliveryDate\": \"2021-06-06\",\"comment\": \"Специальный JSON без окончания\"";
        return given()
                .spec(getBaseSpec())
                .and()
                .body(createOrderBody + color + "}")
                .when()
                .post("/api/v1/orders");
    }
}