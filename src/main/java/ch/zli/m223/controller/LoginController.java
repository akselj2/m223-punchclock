package ch.zli.m223.controller;

import javax.inject.Inject;
import javax.print.attribute.standard.MediaTray;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.annotations.Producer;

import ch.zli.m223.model.Login;
import ch.zli.m223.model.UserEntity;
import ch.zli.m223.service.LoginService;
import ch.zli.m223.service.UserService;

@Path("/login")
@Tag(name = "Login", description = "Loginpage")
public class LoginController {
    @Inject
    LoginService loginService;

    @Inject
    UserService userService;


    /**
     * 
     * @param login parameter of the login entity. makes it more readable and a bit more understandable for me.
     * @return returns the JWT token that's gotten from the LoginService.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "login", description = "as it says, login.")
    public String login(Login login) {
        var query = userService.findAll();

        var token = "Unsuccessful login.";

        for (UserEntity user : query) {
            token = "gamers, we're in.";
            String userEmail = user.getUsername();
            String userPass = user.getPassword();
            System.out.println("Username: " + userEmail + ", Password: " + userPass);

            if (userEmail.equals(login.getUsername()) && userPass.equals(login.getPassword())) {
                token = loginService.login(user);
                System.out.println(loginService.login(user));
            }
        }

        return token;

    }
}
