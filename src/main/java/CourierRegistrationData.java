import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierRegistrationData { //избавился от файлов, добавив классы для получения тел
    public final String login;
    public final String password;
    public final String firstName;


    public CourierRegistrationData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    @Step("Получение случайных данных для регистрации.")
    public static CourierRegistrationData getRandomRegistrationData () {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierRegistrationData(login, password, firstName);
    }
}
