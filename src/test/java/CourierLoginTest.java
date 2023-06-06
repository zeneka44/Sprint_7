import client.CourierClient;
import data.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest extends TestBase {
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
    public void loginCourier() {
        CourierClient.login(login, password)
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void loginCourierWithMissingLogin() {
        CourierClient.login("", password)
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithMissingPassword() {
        CourierClient.login(login, "")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithInvalidCredentials() {
        CourierClient.login(
                        faker.lorem().fixedString(15),
                        faker.lorem().fixedString(10)
                )
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
