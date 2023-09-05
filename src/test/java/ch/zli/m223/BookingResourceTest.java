package ch.zli.m223;

import org.junit.jupiter.api.Test;

import ch.zli.m223.model.UserEntity;
import ch.zli.m223.service.AuthenticationService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BookingResourceTest {
    @Test
    public void testBookingGet() {
    given()
        .when().get("/buchungen")
        .then()
        .statusCode(401)
        .body(is(""));
  }

  @Test
  public void testBookingGetJwtAdmin() {//fails
    UserEntity user = new UserEntity();

    user.setId(Long.parseLong("2"));
    user.setUsername("Rosa");
    user.setPassword("123456");
    try {
        user.setEmail("test@admin.test");
    } catch(Exception e) {

    }

    user.setRole("Admin");

    String token = AuthenticationService.returnkey(user);
    given()
        .header("Authorization: ", "Bearer " + token)
        .when().get("/buchungen")
        .then()
        .statusCode(200) //Status code that is returned is 401 Unauthorized. Don't quite know why.
        .body(is("[]"));
  }

  @Test
  public void testBookingGetJwtMember() { //fails
      UserEntity user = new UserEntity();
      user.setId(Long.parseLong("3"));
      user.setUsername("Friedrich");
      user.setPassword("123456");
      try {
          user.setEmail("test@member.test");
      } catch (Exception e) {

      }
      user.setRole("Member");
      String token = AuthenticationService.returnkey(user);
      given()
        .header("Authorization: ", "Bearer " + token)
        .when().get("/buchungen")
        .then()
        .statusCode(200) // is 401 Unauthorized. Same test failure as previous test.
        .body(is("[]"));
  }

  @Test
  public void testGetBookingById() { // works
      given()
        .when().get("/buchungen/1")
        .then()
        .statusCode(401)
        .body(is(""));
  }
}
