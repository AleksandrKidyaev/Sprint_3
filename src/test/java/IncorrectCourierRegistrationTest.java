import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class IncorrectCourierRegistrationTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    String courierPassword = RandomStringUtils.randomAlphabetic(10);
    String courierLogin = RandomStringUtils.randomAlphabetic(10);
    String courierFirstName = RandomStringUtils.randomAlphabetic(10);
    String bodyWithoutLogin = "{\"password\":\"" + courierPassword + "\","
            + "\"firstName\":\"" + courierFirstName + "\"}";
    String bodyWithoutPassword = "{\"login\":\"" + courierLogin + "\","
            + "\"firstName\":\"" + courierFirstName + "\"}";
    String bodyWithouFirstName = "{\"login\":\"" + courierPassword + "\","
            + "\"password\":\"" + courierFirstName + "\"}";

    @Test
    public void checkCourierRegistrationWithoutLoginTest() {
        Response responseWitoutLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyWithoutLogin)
                .when()
                .post("/api/v1/courier");
        responseWitoutLogin.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

    }

    @Test
    public void checkCourierRegistrationWithoutPassword() {
        Response responseWithoutPassword = given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyWithoutPassword)
                .when()
                .post("/api/v1/courier");
        responseWithoutPassword.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}
