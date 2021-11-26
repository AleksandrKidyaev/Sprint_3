import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierAuthorizationTest { //эндпойнт /api/v1/courier/login

    private CourierMethods courierMethods;

    @Before
    public void setUp () {
        courierMethods = new CourierMethods();
    }

    @Test
    @DisplayName("Успешная авторизация курьера.")
    @Description("Тест корректности ответа при авторизации нового курьера для эндпойнта /api/v1/courier/login.")
    public void checkResponseAfterCorrectCourierAuthorizationTest() {
        CourierRegistrationData courierRegistrationData = CourierRegistrationData.getRandomRegistrationData();
        courierMethods.registerNewCourier(courierRegistrationData);
        Response response = courierMethods.courierAuthorization(CourierAuthorizationData.from(courierRegistrationData));
        int courierId = courierMethods.returnCourierId(CourierAuthorizationData.from(courierRegistrationData));
        response.then().assertThat().body("id", equalTo(courierId)).and().statusCode(200);
        //немного изменил проверку, чтобы проверялось не то что id существует, а то что он совпадает с корректным
        courierMethods.deleteCourier(courierId);
        }

    @Test
    @DisplayName("Попытка авторизации несуществующего курьера.")
    @Description("Тест корректности ответа при попытке авторизации несуществующего курьера для эндпойнта /api/v1/courier/login.")
    public void checkResponseAfterCourierAuthorizationWithIncorrectDataTest() {
        CourierAuthorizationData courierAuthorizationData = CourierAuthorizationData.getRandomAuthorizationData();
        Response response = courierMethods.courierAuthorization(courierAuthorizationData);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);
    }

    @Test
    @DisplayName("Попытка авторизации без логина.")
    @Description("Тест корректности ответа при попытке авторизации курьера без указания поля логина для эндпойнта /api/v1/courier/login.")
    public void checkResponseAfterCourierAuthorizationWithoutLoginTest() {
        String bodyWithoutLogin = "{\"password\":\"somepassword\"}";
        Response response = courierMethods.courierAuthorizationWithIncorrectData(bodyWithoutLogin);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }

    @Test(timeout=15000)
    /*
    Добавлен таймаут, т.к. тест не будет возвращать ни положительный ни отрицательный результат
    Если отправить запрос на авторизацию без пароля, то будет идти бесконечная отправка запроса
    Как в Postman`е, так и при выполнении теста. Хотя в документации сказано, что в любом случае
    должен быть ответ 400 с указанным текстом
    */
    @DisplayName("Попытка авторизации без пароля.")
    @Description("Тест корректности ответа при попытке авторизации курьера без указания поля пароля для эндпойнта /api/v1/courier/login.")
    public void checkResponseAfterCourierAuthorizationWithoutPasswordTest() {
        String bodyWithoutPassword = "{\"login\":\"somelogin\"}";
        Response response = courierMethods.courierAuthorizationWithIncorrectData(bodyWithoutPassword);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }

}
