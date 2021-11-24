import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierIncorrectAuthorizationTest {

    private final String incorrectCourierPassword = "incorrectpassword";
    private final String incorrectCourierLogin = "incorrectlogin";
    //также можно было бы использовать логин и пароль из файла, они бы тоже подошли, т.к. курьер по ним не создавался в данном классе
    //либо рандомные значения, это тоже на прохождение бы не повлияло
    @Before
    public void setBaseUri() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    /*Вместо указания baseURI в Before, также рассматривал вариант сделать через отдельный класс с
    RequestSpecBuilder прописав там uri и, например, content type, потом расширить все классы, где это надо
    После чего заменить в действиях contentType("application/json") на .spec(метод())
     */
    @Test
    @DisplayName("Проверка ответа при попытке авторизации курьера по несуществующим логину и паролю")
    public void checkResponseAfterCourierAuthorizationWithIncorrectDataTest() {
        String bodyWithIncorrectLoginAndPassword = "{\"login\":\"" + incorrectCourierLogin + "\","
                + "\"password\":\"" + incorrectCourierPassword + "\"}";
        Response responseIncorrectData = given().contentType("application/json")
                .body(bodyWithIncorrectLoginAndPassword)
                .when()
                .post("/api/v1/courier/login");
        responseIncorrectData.then().assertThat().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);

    }

    @Test
    @DisplayName("Проверка ответа при попытке авторизации курьера без указания поля логина")
    public void checkResponseAfterCourierAuthorizationWithoutLoginTest() {
        String bodyWithoutLogin = "{\"password\":\"" + incorrectCourierPassword + "\"}";
        Response responseWithoutLogin = given().contentType("application/json")
                .body(bodyWithoutLogin)
                .when()
                .post("/api/v1/courier/login");
        responseWithoutLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }

    @Test(timeout=15000)
    /*
    Добавлен таймаут, т.к. тест не будет возвращать ни положительный ни отрицательный результат
    Если отправить запрос на авторизацию без пароля, то будет идти бесконечная отправка запроса
    Как в Postman`е, так и при выполнении теста. Хотя в документации сказано, что в любом случае
    должен быть ответ 400 с указанным текстом
    */
    @DisplayName("Проверка ответа при попытке авторизации курьера без указания поля пароля")
    public void checkResponseAfterCourierAuthorizationWithoutPasswordTest() {
        String bodyWithoutPassword = "{\"login\":\"" + incorrectCourierLogin + "\"}";
        Response responseWithoutLogin = given().contentType("application/json")
                .body(bodyWithoutPassword)
                .when()
                .post("/api/v1/courier/login");
        responseWithoutLogin.then().assertThat().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);
    }


}
