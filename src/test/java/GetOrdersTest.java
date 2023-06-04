import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest {

    @Test
    public void getOrders() {
        baseURI = "https://qa-scooter.praktikum-services.ru";
        given()
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders[0]", notNullValue());
    }
}
