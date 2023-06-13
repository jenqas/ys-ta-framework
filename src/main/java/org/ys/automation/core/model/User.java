package org.ys.automation.core.model;

public class User {

    private String userEmail;
    private String password;

    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user email='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
