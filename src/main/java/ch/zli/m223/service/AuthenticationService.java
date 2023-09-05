package ch.zli.m223.service;

import java.util.Arrays;
import java.util.HashSet;

import ch.zli.m223.model.UserEntity;
import io.smallrye.jwt.build.Jwt;

/**
 * @author Aksel Jessen
 * @date: 5.09.2023
 * @classname: AuthenticationService.java
 */

public class AuthenticationService {

    /**
     * @param user user entity for JWT
     * @return returns JWT
     */
    public static String returnkey (UserEntity user) {
        String token = Jwt.issuer("https://example.com/")
        .upn(user.getId().toString())
        .groups(new HashSet<>(Arrays.asList(user.getRole())))
        .sign();

        return token;
    }
}
