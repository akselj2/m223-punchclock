package ch.zli.m223;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import javax.swing.text.AbstractDocument.Content;

import ch.zli.m223.model.Login;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

/**
 * @author Aksel Jessen
 * @date 5.09.2023
 * @classname LoginResourceTest.java
 */
@QuarkusTest
public class LoginResourceTest {
    @Test
  public void testLoginFail() {
    // Unsuccessful Login
    Login login = new Login();

    login.setUsername("test@Admin.test");
    login.setPassword("123456");

    given()
        .body(login)
        .contentType(ContentType.JSON)
        .when().post("/login")
        .then()
        .statusCode(200)
        .body(is("Unsuccessful login."));
  }

  @Test
  public void testLoginSuccessAdmin() {
    // Successful Login
    Login login = new Login();

    login.setUsername("Test@Admin.test");
    login.setPassword("123456");

    given()
        .body(login)
        .contentType(ContentType.JSON)
        .when().post("/login")
        .then()
        .statusCode(200);
  }

  @Test
  public void testLoginSuccessMitglied() {
    // Successful Login
    Login login = new Login();

    login.setUsername("Test@Mitglied.test");
    login.setPassword("123456");

    given()
        .body(login)
        .contentType(ContentType.JSON)
        .when().post("/login")
        .then()
        .statusCode(200);
  }
}
