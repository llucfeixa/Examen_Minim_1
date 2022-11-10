package edu.upc.dsa.models;

public class MyObject {
    String objectId;
    String name;
    String description;
    double coins;

    public MyObject() {}

    public MyObject(String objectId, String name, String description, double coins) {
        this.objectId = objectId;
        this.name = name;
        this.description = description;
        this.coins = coins;
    }

    public String getId() {
        return this.objectId;
    }

    public void setId(String objectId) { this.objectId = objectId; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) { this.description = description; }

    public double getCoins() {
        return this.coins;
    }

    public void setCoins(double coins) { this.coins = coins; }
}
