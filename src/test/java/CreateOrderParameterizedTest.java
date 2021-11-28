import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
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

    @Epic(value = "API Самоката")
    @Feature(value = "Заказ")
    @Story(value = "Создание заказа")
    @Test
    @DisplayName("Создание заказа с указанием различных вариантов цвета.")
    @Description("Тест корректности ответа при создании нового заказа с использованием нескольких вариантов поля цвета для эндпойнта /api/v1/orders.")
    @Owner(value = "Кидяев Александр Дмитриевич")
    @Severity(value = SeverityLevel.MINOR)
    public void checkResponseForCreatingOrderWithDifferentColorsTest () {
        OrderMethods createOrder = new OrderMethods();
        Response response = createOrder.createOrderWithSpecificColor(color);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }

}
