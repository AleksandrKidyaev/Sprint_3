import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierIncorrectAuthorizationTest {
    @Before
    public void setUrlAndRegisterNewCourier() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void checkResponseAfterCourierAuthorizationWithIncorrectDataTest() {
        String incorrectCourierPassword = "incorrectpassword";
        String incorrectCourierLogin = "incorrectlogin";
        String bodyWithIncorrectLoginAndPassword = "{\"login\":\"" + incorrectCourierLogin + "\","
                + "\"password\":\"" + incorrectCourierPassword + "\"}";
        String bodyWithoutLogin = "{\"password\":\"" + incorrectCourierPassword + "\"}";

        Response responseIncorrectData = given().contentType("application/json")
                .body(bodyWithIncorrectLoginAndPassword)
                .when()
                .post("/api/v1/courier/login");
        responseIncorrectData.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);

        Response responseWithoutLogin = given().contentType("application/json")
                .body(bodyWithoutLogin)
                .when()
                .post("/api/v1/courier/login");
        responseWithoutLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);

    }
}
