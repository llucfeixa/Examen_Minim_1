package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class User {
    String name;
    String surname;
    String date;
    Credentials credentials;
    double coins;
    List<MyObject> myObjects;

    public User() {
    }

    public User(String name, String surname, String date, Credentials credentials) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.credentials = credentials;
        this.coins = 50;
        this.myObjects = new LinkedList<>();
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

    public double getCoins() {
        return this.coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public List<MyObject> getObjects() {
        return this.myObjects;
    }

    public void setObjects(List<MyObject> myObjects) {
        this.myObjects = myObjects;
    }

    public void addObject(MyObject myObject) {
        this.myObjects.add(myObject);
        this.coins = this.coins - myObject.getCoins();
    }
}
