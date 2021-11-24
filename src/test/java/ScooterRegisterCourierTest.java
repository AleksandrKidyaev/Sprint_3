import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
public class ScooterRegisterCourierTest {

    @Test
    @DisplayName("Проверка факта наличия сведений в результате выполнения метода registerNewCourierAndReturnLoginPassword")
    public void registerNewCourierAndReturnLoginPasswordTest() {

        ScooterRegisterCourier testCourier = new ScooterRegisterCourier();
        ArrayList<String> registerResult = testCourier.registerNewCourierAndReturnLoginPassword();
        assertNotNull(registerResult);

    }


}