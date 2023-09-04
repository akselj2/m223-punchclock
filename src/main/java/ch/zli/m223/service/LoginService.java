package ch.zli.m223.service;
import javax.enterprise.context.ApplicationScoped;

import ch.zli.m223.model.UserEntity;

@ApplicationScoped
public class LoginService {
    public String login (UserEntity user) {
        var token = "";

        token = AuthenticationService.returnkey(user);

        return token;
    }
}