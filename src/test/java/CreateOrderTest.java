import com.google.gson.Gson;
import data.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static utils.Generator.randomInt;
import static utils.Generator.randomString;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    public static final String ORDERS_ENDPOINT = "/api/v1/orders";
    private final String[] colors;

    public CreateOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{""}}
        };
    }

    @Before
    public void setUp() {
        baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createOrderWithDifferentColors() {
        Order order = new Order(randomString(), randomString(), randomString(), randomInt(), randomString(),
                randomInt(), randomString(), randomString(), colors);
        given()
                .and()
                .body(new Gson().toJson(order))
                .when()
                .post(ORDERS_ENDPOINT)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}

