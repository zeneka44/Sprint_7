package client;

import com.google.gson.Gson;
import data.Courier;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CourierClient {

    public static final String COURIER_ENDPOINT = "/api/v1/courier";
    public static final String COURIER_ID_ENDPOINT = COURIER_ENDPOINT + "/{id}";
    public static final String COURIER_LOGIN_ENDPOINT = COURIER_ENDPOINT + "/login";

    public static Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(courier))
                .when()
                .post(COURIER_ENDPOINT);
    }

    public static Response login(String login, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("password", password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(new Gson().toJson(map))
                .when()
                .post(COURIER_LOGIN_ENDPOINT);
    }

    public static int getId(String login, String password) {
        return login(login, password)
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract().body().path("id");
    }

    public static Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(COURIER_ID_ENDPOINT, id);
    }
}
