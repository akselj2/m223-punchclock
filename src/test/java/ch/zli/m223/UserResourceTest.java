package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import ch.zli.m223.model.UserEntity;
import ch.zli.m223.service.AuthenticationService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserResourceTest {

    @Test
    public void testUserGet() {
        given()
            .when().get("/user")
            .then()
            .statusCode(200)
            .body(is("[]"));
    }

    @Test
    public void testUserGetJWT() {

        UserEntity user = new UserEntity();
        user.setId(Long.parseLong("1"));
        user.setUsername("Karl");
        user.setPassword("123456");
        try {
            user.setEmail("testt@admin.test");
        } catch (Exception e) {

        }
        user.setRole("Admin");

        String token = AuthenticationService.returnkey(user);
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/user")
                .then()
                .statusCode(200);
    }

}