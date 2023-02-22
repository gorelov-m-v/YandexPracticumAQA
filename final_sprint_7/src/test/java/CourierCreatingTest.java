import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class CourierCreatingTest {
    Faker faker = new Faker();
    private String randomCourierLoginFirst;
    private String randomCourierPasswordFirst;
    private String randomCourierFirstNameFirst;
    private String randomCourierPasswordSecond;
    private String randomCourierFirstNameSecond;
    private int statusCode;
    private int courierId;
    private String message;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        Faker faker = new Faker();
        randomCourierLoginFirst = faker.name().lastName() + faker.number().digits(3);
        randomCourierPasswordFirst = faker.number().digits(8);
        randomCourierFirstNameFirst = faker.name().firstName();
        randomCourierPasswordSecond = faker.number().digits(8);
        randomCourierFirstNameSecond = faker.name().firstName();
    }

    @Test
    @DisplayName("Проверка статус-кода и тела ответа, при создании курьера:")
    @Description("Набор валидных данных.")
    public void positiveTestShouldReturn_201AndWrightBody() {
        Courier courier = new Courier(randomCourierLoginFirst, randomCourierPasswordFirst, randomCourierFirstNameFirst);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response.then().statusCode(201);
        message = response.getBody().asString();
        Assert.assertEquals("{\"ok\":true}", message);

        statusCode = response.getStatusCode();
    }

    @Test
    @DisplayName("Проверка статус-кода и тела ответа, при создании двух идентичных курьеров:")
    @Description("Поля Логин, Пароль, Имя должны полностью совпадать.")
    public void identicalCourierShouldReturn_409AndWrightBody() {
        Courier courier = new Courier(randomCourierLoginFirst, randomCourierPasswordFirst, randomCourierFirstNameFirst);

        Response firstResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        Response secondResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        secondResponse.then().statusCode(409);
        message = secondResponse.getBody().asString();
        Assert.assertEquals("{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}", message);

        statusCode = firstResponse.getStatusCode();
    }

    @Test
    @DisplayName("Проверка статус-кода и тела ответа, при создании курьера без указания логина:")
    @Description("Передаем только Пароль и Имя.")
    public void creatingCourierWithoutLoginShouldReturn_400AndWrightBody() {
        CourierWithoutLogin courierWithoutLogin = new CourierWithoutLogin(randomCourierPasswordFirst, randomCourierFirstNameFirst);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutLogin)
                .when()
                .post("/api/v1/courier");

        response.then().statusCode(400);
        message = response.getBody().asString();
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}", message);

        statusCode = response.getStatusCode();
    }

    @Test
    @DisplayName("Проверка статус-кода и тела ответа, при создании курьера без указания пароля:")
    @Description("Передаем только Логин и Имя.")
    public void creatingCourierWithoutPasswordShouldReturn_400AndWrightBody() {
        CourierWithoutLogin courierWithoutLogin = new CourierWithoutLogin(randomCourierLoginFirst, randomCourierFirstNameFirst);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutLogin)
                .when()
                .post("/api/v1/courier");

        response.then().statusCode(400);
        message = response.getBody().asString();
        Assert.assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}", message);

        statusCode = response.getStatusCode();
    }

    @Test
    @DisplayName("Проверка статус-кода и тела ответа, при создании курьера без указания поля Имя:")
    @Description("Передаем только Логин и Пароль, ждем 201.")
    public void creatingCourierWithoutFirstNameShouldReturn_201AndWrightBody() {
        CourierWithoutFirstName courierWithoutFirstName = new CourierWithoutFirstName(randomCourierLoginFirst, randomCourierPasswordFirst);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutFirstName)
                .when()
                .post("/api/v1/courier");

        response.then().statusCode(201);
        message = response.getBody().asString();
        Assert.assertEquals("{\"ok\":true}", message);

        statusCode = response.getStatusCode();
    }

    @Test
    @DisplayName("Проверка статус-кода и тела ответа, при создании двух курьеров с одинаковыми полями Логин:")
    @Description("Нельзя создать курьеров с одинаковыми полями Логин")
    public void creatingCourierWithSameLoginShouldReturn_409AndWrightBody() {
        Courier courierFirst = new Courier(randomCourierLoginFirst, randomCourierPasswordFirst, randomCourierFirstNameFirst);
        Courier courierSecond = new Courier(randomCourierLoginFirst, randomCourierPasswordSecond, randomCourierFirstNameSecond);

        Response firstResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierFirst)
                .when()
                .post("/api/v1/courier");

        Response secondResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierSecond)
                .when()
                .post("/api/v1/courier");

        secondResponse.then().statusCode(409);
        message = secondResponse.getBody().asString();
        Assert.assertEquals("{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}", message);
        
        statusCode = firstResponse.getStatusCode();
    }

    @After
    public void tearDown() {
        if (statusCode == 201) {
            CourierLogin courierLogin = new CourierLogin(randomCourierLoginFirst, randomCourierPasswordFirst);
            Response responseLogin = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(courierLogin)
                    .when()
                    .post("/api/v1/courier/login");
            JsonPath jsnPath = responseLogin.jsonPath();
            courierId = jsnPath.get("id");

            Response responseDelete = given()
                    .header("Content-type", "application/json")
                    .when()
                    .delete(String.format("/api/v1/courier/%s", courierId));

            responseDelete.then().statusCode(200);
        }
    }
}

