import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierAuthorizationData { //избавился от файлов, добавив классы для получения тел

        public final String login;
        public final String password;

        public CourierAuthorizationData(String login, String password) {
            this.login = login;
            this.password = password;
        }
    @Step("Получение случайных данных для авторизации.")
    public static CourierAuthorizationData getRandomAuthorizationData () {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierAuthorizationData(login, password);
    }
    @Step("Получение логина и пароля из данных о регистрации.")
    public static CourierAuthorizationData from(CourierRegistrationData courierRegistrationData) {
            return new CourierAuthorizationData(courierRegistrationData.login, courierRegistrationData.password);
    }
}
