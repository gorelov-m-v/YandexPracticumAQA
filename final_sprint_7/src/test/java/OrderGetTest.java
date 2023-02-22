import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class OrderGetTest {
    private String randomCourierLoginFirst;
    private String randomCourierPasswordFirst;
    private String randomCourierFirstNameFirst;
    private int courierId;
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

        Faker faker =new Faker();
        randomCourierLoginFirst = faker.name().lastName() + faker.number().digits(3);
        randomCourierPasswordFirst = faker.number().digits(8);
        randomCourierFirstNameFirst = faker.name().firstName();

        Courier courier = new Courier(randomCourierLoginFirst, randomCourierPasswordFirst, randomCourierFirstNameFirst);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        CourierLogin courierLogin = new CourierLogin(randomCourierLoginFirst, randomCourierPasswordFirst);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");
        JsonPath jsnPath = response.jsonPath();
        courierId = jsnPath.get("id");


    }
    @Test
    @DisplayName("Проверка статус-кода и тела ответа,c указанием id курьера без заказов.")
    @Description("Указываем id свежесозданного курьера.")
    public void smokeTestGetOrdersWithoutOrders() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get(String.format("/api/v1/orders?courierId=%s", courierId));

        response.then().assertThat()
                .statusCode(200);
        System.out.println(response.body().asString());
    }
    @Test
    @DisplayName("Проверка статус-кода и тела ответа,c указанием id курьера без заказов.")
    @Description("Не указываем id курьера в запросе.")
    public void smokeTestGetAll() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get(String.format("/api/v1/orders"));

        response.then().assertThat()
                .statusCode(200);
        System.out.println(response.body().toString());
    }
    @After
    public void tearDown() {
        Response responseDelete = given()
                    .header("Content-type", "application/json")
                    .when()
                    .delete(String.format("/api/v1/courier/%s", courierId));

            responseDelete.then().statusCode(200);
        }
}
