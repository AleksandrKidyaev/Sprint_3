import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCourierTest { //эндпойнт /api/v1/courier/:id

    private CourierMethods courierMethods;
    private int courierId;

    @Before
    public void setUp () {
        courierMethods = new CourierMethods();
    }

    @Test
    @DisplayName("Удаление курьера.")
    @Description("Тест корректности ответа при удалении существующего курьера для эндпойнта /api/v1/courier/:id.")
    public void checkResponseAfterDeletingCoutier () {
        CourierRegistrationData courierRegistrationData = CourierRegistrationData.getRandomRegistrationData();
        courierMethods.registerNewCourier(courierRegistrationData);
        courierId = courierMethods.returnCourierId(CourierAuthorizationData.from(courierRegistrationData));
        Response response = courierMethods.deleteCourier(courierId);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Попытка авторизации под удаленным курьером.")
    @Description("Тест корректности ответа при попытке авторизации под только что удаленным курьером для эндпойнта /api/v1/courier/:id.")
    public void checkAbsenceOfDeletedCourierTest() {
        CourierRegistrationData courierRegistrationData = CourierRegistrationData.getRandomRegistrationData();
        courierMethods.registerNewCourier(courierRegistrationData);
        courierId = courierMethods.returnCourierId(CourierAuthorizationData.from(courierRegistrationData));
        courierMethods.deleteCourier(courierId);
        Response response = courierMethods.courierAuthorization(CourierAuthorizationData.from(courierRegistrationData));
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }
}
