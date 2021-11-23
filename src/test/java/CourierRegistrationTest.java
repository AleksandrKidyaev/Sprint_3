import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ReturnCourierIdTest {



    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown () {
        CourierDataForTest data = new CourierDataForTest();
        int courierId = data.returnCourierId();

        Response delete = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId);
        delete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }


    @Test
    public void registerNewCourierTest() {

        File courierRegistrationData = new File("src/main/resources/CourierRegistrationData");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierRegistrationData)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);


    }

    @Test
    public void checkRegistrationOfSecondCourierWithSameParametersTest() {

        File courierRegistrationData = new File("src/main/resources/CourierRegistrationData");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierRegistrationData)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        Response secondResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierRegistrationData)
                .when()
                .post("/api/v1/courier");
        secondResponse.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
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