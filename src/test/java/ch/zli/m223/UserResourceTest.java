package ch.zli.m223;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import ch.zli.m223.model.UserEntity;
import ch.zli.m223.service.AuthenticationService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


/**
 * @author Aksel Jessen
 * @date 05.09.2023
 * @classname UserResourceTest.java
 */
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
                .header("Authorization: ", "Bearer " + token)
                .when().get("/user")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {

        given()
                .when().delete("/user/1")
                .then()
                .statusCode(401);

    }

    @Test
    public void testUpdate() {

        given()
                .when().put("/user/1")
                .then()
                .statusCode(401);

    }

    @Test
    public void testUpdateJWT() {

        UserEntity user = new UserEntity();
        user.setId(Long.parseLong("4"));
        user.setUsername("Leon");
        user.setPassword("123456");
        try {
            user.setEmail("test@admin.test");
        } catch (Exception e) {

        }
        user.setRole("Admin");

        String token = AuthenticationService.returnkey(user);

        given()
                .body(user)
                .contentType(ContentType.JSON)
                .header("Authorization: ", "Bearer " + token)
                .when().put("/user/4")
                .then()
                .statusCode(200);
    }

}