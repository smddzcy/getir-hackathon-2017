package com.wow.wowmeet.models;

import java.io.Serializable;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class User implements Serializable {
    private String userId;
    private String name;
    private String email;
    private String password;

    public String getToken() {
        return token;
    }

    private String token;

    private User(String userId, String name, String email, String password, String token) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        private String name;
        private String email;
        private String password;
        private String token;

        public UserBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
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
            return new User(userId, name, email, password, token);
        }
    }

    @Override
    public String toString() {
        return getEmail() + " " + getName();
    }
}
