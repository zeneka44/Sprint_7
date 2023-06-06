import client.OrderClient;
import data.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static java.util.concurrent.TimeUnit.DAYS;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends TestBase {
    private final String[] colors;

    public CreateOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{""}}
        };
    }

    @Test
    public void createOrderWithDifferentColors() {
        Order order = new Order(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                faker.random().nextInt(1, 100),
                faker.phoneNumber().phoneNumber(),
                faker.random().nextInt(1, 100),
                faker.date().future(faker.random().nextInt(1, 10), DAYS).toString(),
                faker.lorem().fixedString(10),
                colors);
        OrderClient.create(order)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}

