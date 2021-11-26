import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest { //эндпойнт api/v1/orders

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
    @DisplayName("Создание заказа с указанием различных вариантов цвета.")
    @Description("Тест корректности ответа при создании нового заказа с использованием нескольких вариантов поля цвета для эндпойнта /api/v1/orders.")
    public void checkResponseForCreatingOrderWithDifferentColorsTest () {
        OrderMethods createOrder = new OrderMethods();
        Response response = createOrder.createOrderWithSpecificColor(color);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

}
