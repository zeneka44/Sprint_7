import client.OrderClient;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest extends TestBase {

    @Test
    public void getOrders() {
        OrderClient.getList()
                .then()
                .statusCode(200)
                .body("orders[0]", notNullValue());
    }
}
