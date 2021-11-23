import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ScooterRegisterCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void registerNewCourierAndReturnLoginPasswordTest() {

        ScooterRegisterCourier testCourier = new ScooterRegisterCourier();
        ArrayList<String> registerResult = testCourier.registerNewCourierAndReturnLoginPassword();
        assertNotNull(registerResult);

    }


}