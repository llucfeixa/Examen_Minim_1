package edu.upc.dsa.models;

public class UserRegister {
    String name;
    String surname;
    String date;
    Credentials credentials;

    public UserRegister() {
    }

    public UserRegister(String name, String surname, String date, Credentials credentials) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.credentials = credentials;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
