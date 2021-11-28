import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;

public class CourierMethods extends RestAssuredSpecification{

    private static final String endpointUrl = "/api/v1/courier/";

    @Attachment
    public static byte[] getSomeDoge(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources", resourceName));
    }
    @Step
    public void getScreenshot () throws IOException {
        getSomeDoge("veryTest.png");
    }

    @Step("Регистрация нового курьера.")
    public Response registerNewCourier (CourierRegistrationData registrationData) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(registrationData)
                .when()
                .post(endpointUrl);
    }

    @Step("Регистрация нового курьера.")
    public Response registerNewCourierWithIncorrectData (String registrationData) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(registrationData)
                .when()
                .post(endpointUrl);
    }

    @Step("Получение id курьера после авторизации.")
    public int returnCourierId (CourierAuthorizationData authorizationData) {
            return given()
                    .spec(getBaseSpec())
                    .body(authorizationData)
                    .when()
                    .post(endpointUrl + "login")
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
                .post(endpointUrl + "login");
    }

    @Step("Авторизация.")
    public Response courierAuthorizationWithIncorrectData (String incorrectBody) {
        return given()
                .spec(getBaseSpec())
                .body(incorrectBody)
                .when()
                .post(endpointUrl + "login");
    }

    @Step("Удаление курьера.")
    public Response deleteCourier (int courierId) {
        Response delete = null;
        if (courierId != 0) { //if написал, чтобы метод не удалял курьера с нулевым id, в тех случаях когда новый курьер не создается, но After все равно отрабатывает
        delete = given()
                .spec(getBaseSpec())
                .when()
                .delete(endpointUrl + courierId);
        }
        return delete;
    }

    }

