import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestAssuredSpecification { //создал класс спецификации, чтобы не прописывать везде contenttype и uri

    private static final String baseUri = "http://qa-scooter.praktikum-services.ru";

    public RequestSpecification getBaseSpec () {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUri)
                .build();
    }
}
