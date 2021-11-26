import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderList extends RestAssuredSpecification {

    @Test //Проверка условия "Проверь, что в тело ответа возвращается список заказов."
    @DisplayName("Проверка ответа на запрос о десяти доступных заказах")
    public void checkResponseForGetTenAvailableOrdersTest () {
        Response response =
                given()
                .spec(getBaseSpec())
                .get("/api/v1/orders?limit=10&page=0");
        response.then().assertThat().body("orders", notNullValue()).and().statusCode(200);
    }
}
