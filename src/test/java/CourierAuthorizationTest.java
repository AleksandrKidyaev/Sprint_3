import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierAuthorizationTest { //эндпойнт /api/v1/courier/login

    private CourierMethods courierMethods;

    @Before
    public void setUp () {
        courierMethods = new CourierMethods();
    }

    @Epic(value = "API Самоката")
    @Feature(value = "Курьер")
    @Story(value = "Авторизация курьера")
    @Test
    @DisplayName("Успешная авторизация курьера.")
    @Description("Тест корректности ответа при авторизации нового курьера для эндпойнта /api/v1/courier/login.")
    @Owner(value = "Кидяев Александр Дмитриевич")
    @Severity(value = SeverityLevel.BLOCKER)
    public void checkResponseAfterCorrectCourierAuthorizationTest() {
        CourierRegistrationData courierRegistrationData = CourierRegistrationData.getRandomRegistrationData();
        courierMethods.registerNewCourier(courierRegistrationData);
        Response response = courierMethods.courierAuthorization(CourierAuthorizationData.from(courierRegistrationData));
        int courierId = courierMethods.returnCourierId(CourierAuthorizationData.from(courierRegistrationData));
        response.then().assertThat().body("id", equalTo(courierId)).and().statusCode(SC_OK);
        //немного изменил проверку, чтобы проверялось не то что id существует, а то что он совпадает с корректным
        courierMethods.deleteCourier(courierId);
        }

    @Epic(value = "API Самоката")
    @Feature(value = "Курьер")
    @Story(value = "Авторизация курьера")
    @Test
    @DisplayName("Попытка авторизации несуществующего курьера.")
    @Description("Тест корректности ответа при попытке авторизации несуществующего курьера для эндпойнта /api/v1/courier/login.")
    @Owner(value = "Кидяев Александр Дмитриевич")
    @Severity(value = SeverityLevel.MINOR)
    public void checkResponseAfterCourierAuthorizationWithIncorrectDataTest() {
        CourierAuthorizationData courierAuthorizationData = CourierAuthorizationData.getRandomAuthorizationData();
        Response response = courierMethods.courierAuthorization(courierAuthorizationData);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @Epic(value = "API Самоката")
    @Feature(value = "Курьер")
    @Story(value = "Авторизация курьера")
    @Test
    @DisplayName("Попытка авторизации без логина.")
    @Description("Тест корректности ответа при попытке авторизации курьера без указания поля логина для эндпойнта /api/v1/courier/login.")
    @Owner(value = "Кидяев Александр Дмитриевич")
    @Severity(value = SeverityLevel.MINOR)
    public void checkResponseAfterCourierAuthorizationWithoutLoginTest() {
        String bodyWithoutLogin = "{\"password\":\"somepassword\"}";
        Response response = courierMethods.courierAuthorizationWithIncorrectData(bodyWithoutLogin);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(SC_BAD_REQUEST);
    }



}
