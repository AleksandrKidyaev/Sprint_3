import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class IncorrectCourierRegistrationTest extends RestAssuredSpecification{

    private final String courierPassword = RandomStringUtils.randomAlphabetic(10);
    private final String courierLogin = RandomStringUtils.randomAlphabetic(10);
    private final String courierFirstName = RandomStringUtils.randomAlphabetic(10);
    /*
    Аналогично комментарию из класса CourierIncorrectAuthorizationTest: рассматривал вариант использовать логин, пароль и имя из файла,
    они бы тоже подошли, т.к. курьер по ним не создавался в данном классе
    либо вместо генерации присвоить любые подходящие значения вручную. Либо получать из отдельного
    класса, созданного специально для этого
    */


    @Test
    @DisplayName("Проверка ответа на попытку регистрации курьера без поля логина")
    public void checkCourierRegistrationWithoutLoginTest() {
        String bodyWithoutLogin = "{\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        Response responseWitoutLogin = given()
                .spec(getBaseSpec())
                .and()
                .body(bodyWithoutLogin)
                .when()
                .post("/api/v1/courier");
        responseWitoutLogin.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

    }

    @Test
    @DisplayName("Проверка ответа на попытку регистрации курьера без поля пароля")
    public void checkCourierRegistrationWithoutPasswordTest() {
        String bodyWithoutPassword = "{\"login\":\"" + courierLogin + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";
        Response responseWithoutPassword = given()
                .spec(getBaseSpec())
                .and()
                .body(bodyWithoutPassword)
                .when()
                .post("/api/v1/courier");
        responseWithoutPassword.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    /*
    Аналогичным образом можно было бы сделать тест на регистрацию без поля имени.
    Но тут не ясно на что конкретно проверять, т.к. в документации, с одной стороны, поле firstname
    не указано как необязательное, т.е. вроде как тоже должно быть "Недостаточно данных для создания учетной записи".
    С другой стороны, в той же самой документации указано, что ответ 400 возвращается исключительно если нет логина или пароля,
    про firstname не говорится, что вроде как означает возможность успешной регистрации и без имени.
    Так или иначе, тест был бы практически идентичный предыдущим.
     */
}
