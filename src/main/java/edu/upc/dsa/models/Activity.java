package edu.upc.dsa.models;

public class Activity {
    int level;
    int points;
    String date;

    public Activity() {}

    public Activity(int level, int points, String date) {
        this.level = level;
        this.points = points;
        this.date = date;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
