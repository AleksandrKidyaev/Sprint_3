import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    @Before
    public void setUrlAndRegisterNewCourier() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        CourierMethods data = new CourierMethods();
        data.registerNewCourier();
    }

    @After
    public void deleteCourier () {
        CourierMethods data = new CourierMethods();
        data.deleteCourier();
    }



    @Test
    public void checkResponseAfterCorrectCourierAuthorizationTest() {
        File courierAuthorizationData = new File("src/main/resources/CourierAuthorizationData");
        Response response = given().contentType("application/json")
                .body(courierAuthorizationData)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue()).and().statusCode(200);
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
        responseIncorrectData.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(200);

        Response responseWithoutLogin = given().contentType("application/json")
                .body(bodyWithoutLogin)
                .when()
                .post("/api/v1/courier/login");
        responseWithoutLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);

    }
}
