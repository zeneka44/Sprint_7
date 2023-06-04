import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.Generator;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    public static final String COURIER_ENDPOINT = "/api/v1/courier";

    private String randomValue;
    private String login;
    private String password;
    private String firstName;

    @Before
    public void setUp() {
        baseURI = "https://qa-scooter.praktikum-services.ru";
        login = Generator.randomString();
        password = Generator.randomString();
        firstName = Generator.randomString();
        String json = "{\"login\": \"" + login + "\", " +
                "\"password\": \"" + password + "\", " +
                "\"firstName\": \"" + firstName + "\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_ENDPOINT)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        String json = "{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}";
        int id = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_ENDPOINT + "/login")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract().body().path("id");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .delete(COURIER_ENDPOINT + "/{id}", id)
                .then()
                .statusCode(200);
    }

    @Test
    public void loginCourier() {
        String json = "{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_ENDPOINT + "/login")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void loginCourierWithMissingLogin() {
        String json = "{\"login\": \"\", \"password\": \"" + password + "\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_ENDPOINT + "/login")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithMissingPassword() {
        String json = "{\"login\": \"" + login + "\", \"password\": \"\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_ENDPOINT + "/login")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithInvalidCredentials() {
        String json = "{\"login\": \"" + Generator.randomString() + "\", \"password\": \"" + Generator.randomString() + "\", \"firstName\":\"" + Generator.randomString() + "\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_ENDPOINT + "/login")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
