import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierMethods extends RestAssuredSpecification{

    @Step("Регистрация нового курьера.")
    public Response registerNewCourier (CourierRegistrationData registrationData) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(registrationData)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Регистрация нового курьера.")
    public Response registerNewCourierWithIncorrectData (String registrationData) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(registrationData)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Получение id курьера после авторизации.")
    public int returnCourierId (CourierAuthorizationData authorizationData) {
            return given()
                    .spec(getBaseSpec())
                    .body(authorizationData)
                    .when()
                    .post("/api/v1/courier/login")
                    .then()
                    .extract()
                    .path("id");
    }

    @Step("Авторизация.")
    public Response courierAuthorization (CourierAuthorizationData authorizationData) {
        return given()
                .spec(getBaseSpec())
                .body(authorizationData)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Авторизация.")
    public Response courierAuthorizationWithIncorrectData (String incorrectBody) {
        return given()
                .spec(getBaseSpec())
                .body(incorrectBody)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера.")
    public Response deleteCourier (int courierId) {
        Response delete = null;
        if (courierId != 0) { //if написал, чтобы метод не удалял курьера с нулевым id, в тех случаях когда новый курьер не создается, но After все равно отрабатывает
        delete = given()
                .spec(getBaseSpec())
                .when()
                .delete("/api/v1/courier/" + courierId);
        }
        return delete;
    }

    }

