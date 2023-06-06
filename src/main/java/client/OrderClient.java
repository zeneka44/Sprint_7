package client;

import com.google.gson.Gson;
import data.Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class OrderClient {
    public static final String ORDERS_ENDPOINT = "/api/v1/orders";

    public static Response create(Order order) {
        return given()
                .and()
                .body(new Gson().toJson(order))
                .when()
                .post(ORDERS_ENDPOINT);

    }

    public static Response getList() {
        return given()
                .when()
                .get(ORDERS_ENDPOINT);
    }
}
