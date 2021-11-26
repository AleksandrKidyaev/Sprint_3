import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierRegistrationTest extends RestAssuredSpecification{

    @After //в конце каждого теста удаляем созданного курьера
    public void deleteCourier () {
        CourierMethods data = new CourierMethods();
        data.deleteCourier();
    }
    //Тесты написаны по аналогии с примерами из тренажера
    @Test
    @DisplayName("Проверка ответа после успешной регистрации курьера")
    public void checkResponseForRegisteringANewCourierTest() {

        File courierRegistrationBody = new File("src/main/resources/CourierRegistrationJsonBody.json");

        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(courierRegistrationBody)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Проверка ответа после попытки регистрации курьера по уже существующим данным")
    public void checkResponseAfterRegistrationOfSecondCourierWithSameParametersTest() {

        File courierRegistrationBody = new File("src/main/resources/CourierRegistrationJsonBody.json");

        Response response = given()
                .spec(getBaseSpec())
                .and()
                .body(courierRegistrationBody)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        Response secondResponse = given()
                .spec(getBaseSpec())
                .and()
                .body(courierRegistrationBody)
                .when()
                .post("/api/v1/courier");
        secondResponse.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);
        /*
        Сообщение по факту в теле ответа при попытке создания дубля курьера
        отличается от требуемого сообщения в документации.
        Поэтому этот тест будет падать, пока сообщение "Этот логин уже используется. Попробуйте другой."
        не исправят в ответе на "Этот логин уже используется" (либо исправят документацию)
         */

    }

}