import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAssuredSpecification { //создал класс спецификации, чтобы не прописывать везде contenttype и uri

    public RequestSpecification getBaseSpec () {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://qa-scooter.praktikum-services.ru")
                .build();
    }
}
