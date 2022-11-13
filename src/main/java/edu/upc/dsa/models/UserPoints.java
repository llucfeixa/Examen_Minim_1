package edu.upc.dsa.models;

public class UserPoints {
    String userId;
    int points;

    public UserPoints() {}

    public UserPoints(String userId, int points) {
        this.userId = userId;
        this.points = points;
    }

    public String getUserId() { return this.userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public int getPoints() { return this.points; }

    public void setPoints(int points) { this.points = points; }
}
