package com.wow.wowmeet.models;

import java.io.Serializable;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class User implements Serializable {
    private String userId;
    private String username;
    private String email;
    private String password;

    public String getToken() {
        return token;
    }

    private String token;

    private User(String userId, String username, String email, String password, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class UserBuilder {
        private String userId;
        private String username;
        private String email;
        private String password;
        private String token;

        public UserBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public User createUser() {
            return new User(userId, username, email, password, token);
        }
    }


}
