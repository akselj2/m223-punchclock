package ch.zli.m223.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Aksel Jessen
 * @date: 5.09.2023
 * @classname: Login.java
 */

public class Login {
    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    /**** GETTERS AND SETTERS ****/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

