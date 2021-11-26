import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest extends RestAssuredSpecification{

    final private String color;

    public CreateOrderParameterizedTest(String colour) {
        this.color = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][] {
                {"," + "\n" + "\"color\": [\"BLACK\"]"},
                {"," + "\n" + "\"color\": [\"GREY\"]"},
                {"," + "\n" + "\"color\": [\"BLACK\", \"GREY\"]"},
                {""},
        };
    }

    @Test
    @DisplayName("Проверка ответа после создания заказа с использованием разных цветов")
    public void checkResponseForCreatingOrderWithDifferentColorsTest () {
        /*
        В соответствии с рекомендацией из тренажера, в главе "Подготовка данных":
        "Если у данных простая структура, их можно хранить прямо в коде автотестов."
        У тела запроса структа достаточно простая и в данном проекте используется только один раз
        По этой причине она тут
         */
        String createOrderBody = "{\"firstName\": \"Alexandr\",\"lastName\": \"Kidyaev\",\"address\": \"Moscow, ul. Lenina, 21\",\"metroStation\": 4,\"phone\": \"+7 915 111 22 33\",\"rentTime\": 6,\"deliveryDate\": \"2021-06-06\",\"comment\": \"Специальный JSON без окончания\"";
        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(createOrderBody + color + "}")
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

}
