import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierRegistrationTest { //эндпойнт /api/v1/courier

    private CourierMethods courierMethods;
    private int courierId;

    @Before
    public void setUp () {
        courierMethods = new CourierMethods();
    }

    @After
    public void tearDown () {
        courierMethods.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Успешная регистрация нового курьера.")
    @Description("Тест корректности ответа при регистрации нового курьера с правильными данными для эндпойнта /api/v1/courier.")
    public void checkResponseForRegisteringANewCourierTest() {
        CourierRegistrationData courierRegistrationData = CourierRegistrationData.getRandomRegistrationData();
        Response response = courierMethods.registerNewCourier(courierRegistrationData);
        response.then().assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
        courierId = courierMethods.returnCourierId(CourierAuthorizationData.from(courierRegistrationData));

    }

    @Test
    @DisplayName("Попытка регистрация курьера по уже существующим данным.")
    @Description("Тест корректности ответа при регистрации нового курьера по уже существующим данным для эндпойнта /api/v1/courier.")
    public void checkResponseAfterRegistrationOfSecondCourierWithSameParametersTest() {
        CourierRegistrationData courierRegistrationData = CourierRegistrationData.getRandomRegistrationData();
        courierMethods.registerNewCourier(courierRegistrationData);
        courierId = courierMethods.returnCourierId(CourierAuthorizationData.from(courierRegistrationData));
        Response response = courierMethods.registerNewCourier(courierRegistrationData);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(SC_GONE);

        /*
        Сообщение по факту в теле ответа при попытке создания дубля курьера
        отличается от требуемого сообщения в документации.
        Поэтому этот тест будет падать, пока сообщение "Этот логин уже используется. Попробуйте другой."
        не исправят в ответе на "Этот логин уже используется" (либо исправят документацию)
         */
    }

    @Test
    @DisplayName("Попытка регистрация курьера без логина.")
    @Description("Тест корректности ответа при регистрации нового курьера без использования поля логина для эндпойнта /api/v1/courier.")
    public void checkCourierRegistrationWithoutLoginTest() {
        String bodyWithoutLogin = "{\"password\":\"somepassword\",\"firstName\":\"somefirstname\"}";
        Response response = courierMethods.registerNewCourierWithIncorrectData(bodyWithoutLogin);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Попытка регистрация курьера без пароля.")
    @Description("Тест корректности ответа при регистрации нового курьера без использования поля пароля для эндпойнта /api/v1/courier.")
    public void checkCourierRegistrationWithoutPasswordTest() {
        String bodyWithoutPassword = "{\"login\":\"somelogin\",\"firstName\":\"somefirstname\"}";
        Response response = courierMethods.registerNewCourierWithIncorrectData(bodyWithoutPassword);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

}