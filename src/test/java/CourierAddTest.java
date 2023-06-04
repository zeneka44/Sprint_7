import com.google.gson.Gson;
import data.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.Generator.randomString;

public class CourierAddTest {
    public static final String COURIER_ENDPOINT = "/api/v1/courier";
    public static final String NOT_ENOUGH_DATA = "Недостаточно данных для создания учетной записи";
    private String login;
    private String password;
    private String firstName;

    @Before
    public void setUp() {
        baseURI = "https://qa-scooter.praktikum-services.ru";
        login = randomString();
        password = randomString();
        firstName = randomString();
        Courier courier = new Courier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(courier))
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
                .when()
                .delete(COURIER_ENDPOINT + "/{id}", id)
                .then()
                .statusCode(200);
    }


    @Test
    public void createNewCourier() {
        Courier courier = new Courier(randomString(), randomString(), randomString());
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(courier))
                .when()
                .post(COURIER_ENDPOINT)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void createDuplicateCourier() {
        Courier courier = new Courier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(courier))
                .when()
                .post(COURIER_ENDPOINT)
                .then()
                .statusCode(409)
                .body("code", equalTo(409))
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createNewCourierWithMissingLogin() {
        Courier courier = new Courier("", randomString(), randomString());
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(courier))
                .when()
                .post(COURIER_ENDPOINT)
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(NOT_ENOUGH_DATA));
    }

    @Test
    public void createNewCourierWithMissingPassword() {
        Courier courier = new Courier(randomString(), "", randomString());
        given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(courier))
                .when()
                .post(COURIER_ENDPOINT)
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo(NOT_ENOUGH_DATA));
    }

}
