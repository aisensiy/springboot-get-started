package hello;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerTest {
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
    public void should_get_status_200_and_get_right_message() throws Exception {
        when().get("/greeting")
            .then().statusCode(200)
            .body("id", equalTo(1))
            .body("message", equalTo("Hello, World"));
    }

    @Test
    public void should_create_greeting_resource_success_and_get_greeting_back() throws Exception {
        Map<String, Object> postParameter = new HashMap<String, Object>() {{
            put("name", "test");
        }};

        given()
            .contentType("application/json")
            .body(postParameter)
            .when().post("/greeting")
            .then().statusCode(201)
            .body("message", equalTo("Hello, test"));

    }
}
