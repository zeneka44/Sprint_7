import client.CourierClient;
import data.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierAddTest extends TestBase {
    public static final String NOT_ENOUGH_DATA = "Недостаточно данных для создания учетной записи";
    private String login;
    private String password;
    private String firstName;

    @Before
    public void setUp() {
        login = faker.lorem().fixedString(15);
        password = faker.lorem().fixedString(10);
        firstName = faker.name().firstName();
        Courier courier = new Courier(login, password, firstName);
        CourierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        CourierClient.delete(CourierClient.getId(login, password))
                .then()
                .statusCode(200);
    }

    @Test
    public void createNewCourier() {
        Courier courier = new Courier(
                faker.lorem().fixedString(15),
                faker.lorem().fixedString(10),
                faker.name().firstName());
        CourierClient.create(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void createDuplicateCourier() {
        Courier courier = new Courier(login, password, firstName);
        CourierClient.create(courier)
                .then()
                .statusCode(409)
                .body("code", equalTo(409))
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createNewCourierWithMissingLogin() {
        Courier courier = new Courier("", faker.lorem().fixedString(10), faker.name().firstName());
        CourierClient.create(courier)
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(NOT_ENOUGH_DATA));
    }

    @Test
    public void createNewCourierWithMissingPassword() {
        Courier courier = new Courier(faker.lorem().fixedString(10), "", faker.name().firstName());
        CourierClient.create(courier)
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(NOT_ENOUGH_DATA));
    }
}
