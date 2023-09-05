package ch.zli.m223.service;
import javax.enterprise.context.ApplicationScoped;

import ch.zli.m223.model.UserEntity;

/**
 * @author Aksel Jessen
 * @date: 5.09.2023
 * @classname: LoginService.java
 */

@ApplicationScoped
public class LoginService {
    /**
     * 
     * @param user user entity data
     * @return returns JWT token
     */
    public String login (UserEntity user) {
        var token = "";

        token = AuthenticationService.returnkey(user);

        return token;
    }
}