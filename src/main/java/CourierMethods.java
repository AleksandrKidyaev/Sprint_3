import io.restassured.response.Response;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierMethods {

    public void registerNewCourier () {
        File courierRegistrationBody = new File("src/main/resources/CourierRegistrationJsonBody");

        given()
                .header("Content-type", "application/json")
                .and()
                .body(courierRegistrationBody)
                .when()
                .post("/api/v1/courier");

    }

    public int returnCourierId () {
            File courierAuthorizationData = new File("src/main/resources/CourierAuthorizationJsonBody");
            return given().contentType("application/json")
                    .body(courierAuthorizationData)
                    .when()
                    .post("/api/v1/courier/login")
                    .then()
                    .extract()
                    .path("id");


    }

    public void deleteCourier () {
        Response delete = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + returnCourierId());
        delete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }

    }

