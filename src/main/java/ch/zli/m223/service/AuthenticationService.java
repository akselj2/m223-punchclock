package ch.zli.m223.service;

import java.util.Arrays;
import java.util.HashSet;

import ch.zli.m223.model.UserEntity;
import io.smallrye.jwt.build.Jwt;

public class AuthenticationService {
    public static String returnkey (UserEntity user) {
        String token = Jwt.issuer("https://example.com/")
        .upn(user.getId().toString())
        .groups(new HashSet<>(Arrays.asList(user.getRole())))
        .sign();

        return token;
    }
}
