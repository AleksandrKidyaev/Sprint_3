import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;

import java.io.File;

import static io.restassured.RestAssured.given;


public class GetCourierId {

        public int returnCourierId () {
            File courierAuthorizationData = new File("src/main/resources/CourierAuthorizationData");
            int courierId = given().contentType("application/json")
                    .body(courierAuthorizationData)
                    .when()
                    .post("/api/v1/courier/login")
                    .then()
                    .extract()
                    .path("id");

            return courierId;
    }

    }

