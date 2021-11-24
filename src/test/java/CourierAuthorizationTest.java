import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAuthorizationTest {

    @Before
    public void setBaseUriAndRegisterNewCourier() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        CourierMethods data = new CourierMethods();
        data.registerNewCourier(); //регистрируем нового курьера
    }

    @After
    public void deleteCourier () {
        CourierMethods data = new CourierMethods();
        data.deleteCourier(); //после прохождения каждого теста удаляем курьера
    }

    @Test
    @DisplayName("Проверка ответа при успешной авторизации курьера")
    public void checkResponseAfterCorrectCourierAuthorizationTest() {
        File courierAuthorizationBody = new File("src/main/resources/CourierAuthorizationJsonBody");
        Response response = given().contentType("application/json")
                .body(courierAuthorizationBody)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue()).and().statusCode(200);
        //проверка на то, что в ответе есть не пустое поле id и статус ответа соответствует документации
        //по аналогии примеров в тренажере
    }


}
