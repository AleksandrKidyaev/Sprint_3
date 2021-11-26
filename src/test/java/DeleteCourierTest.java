import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class DeleteCourierTest extends RestAssuredSpecification{
    //По первому дополнительному заданию часть проверок уже есть в других классах,
    //поэтому в этом идет проверка, на то что курьер действительно удалился и под ним нельзя зайти
    @Test
    @DisplayName("Проверка ответа после попытки авторизоваться используя данные удаленного курьера")
    public void checkAbsenceOfDeletedCourierTest() {
        CourierMethods data = new CourierMethods();
        data.registerNewCourier();
        data.deleteCourier();
        File courierAuthorizationBody = new File("src/main/resources/CourierAuthorizationJsonBody");
        Response response = given()
                .spec(getBaseSpec())
                .body(courierAuthorizationBody)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);
    }
}
