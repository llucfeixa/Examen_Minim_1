package edu.upc.dsa.models;

public class UserRegister {
    String userRegisterId;

    public UserRegister() {
    }

    public UserRegister(String userRegisterId) {
        this.userRegisterId = userRegisterId;
    }

    public String getUserRegisterId() {
        return this.userRegisterId;
    }

    public void setUserRegisterId(String userRegisterId) {
        this.userRegisterId = userRegisterId;
    }
}
