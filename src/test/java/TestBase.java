import com.github.javafaker.Faker;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.baseURI;

public class TestBase {
    protected static Faker faker;

    @BeforeClass
    public static void beforeClass() {
        baseURI = "https://qa-scooter.praktikum-services.ru";
        faker = new Faker();
    }
}
