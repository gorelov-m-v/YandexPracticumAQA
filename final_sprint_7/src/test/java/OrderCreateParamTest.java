import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class OrderCreateParamTest {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    private OrderTrack orderTrack;

    public OrderCreateParamTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Выбранный цвет в заказе: {8}")
    public static Object[][] params() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK", "GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of()},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void checkOrdersResponseBodyTest() {
        OrderCreateParamTest orderCreateParamTest = new OrderCreateParamTest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orderCreateParamTest)
                .when()
                .post("/api/v1/orders");
        orderTrack = response.as(OrderTrack.class);
        response.then().statusCode(201);
    }

//        Метод (PUT /api/v1/orders/cancel) не работает правильно. Тут баг. Метод возвращает 400 на валидный запрос.
//        Включим этот блок, когда почините.
//    @After
//    public void tearDown() {
//        Response response = given()
//                .header("Content-type", "application/json")
//                .and()
//                .body(orderTrack)
//                .when()
//                .put("/api/v1/orders/cancel");
//
//        response.then().statusCode(200);
//    }
}
