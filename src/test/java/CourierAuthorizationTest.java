import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAuthorizationTest {

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
        File courierAuthorizationBody = new File("src/main/resources/CourierJsonBody");
        Response response = given().contentType("application/json")
                .body(courierAuthorizationBody)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().body("id", notNullValue()).and().statusCode(200);
    }


}
