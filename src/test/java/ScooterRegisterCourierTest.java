import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ScooterRegisterCourierTest {

    @Test
    public void registerNewCourierAndReturnLoginPasswordTest() {

        ScooterRegisterCourier testCourier = new ScooterRegisterCourier();
        ArrayList<String> registerResult = testCourier.registerNewCourierAndReturnLoginPassword();
        assertNotNull(registerResult);

    }


}