package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Partida {
    String gameId;
    int points;
    int levels;
    String date;
    List<Activity> activitites;

    public Partida() {
    }

    public Partida(String gameId) {
        this.gameId = gameId;
        this.points = 50;
        this.levels = 1;
        this.activitites = new ArrayList<>();
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevels() {
        return this.levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Activity> getActivities() {
        return this.activitites;
    }

    public void setActivitites(List<Activity> activitites) {
        this.activitites = activitites;
    }

    public void setActivity(Activity activity) {
        this.activitites.add(activity);
    }
}
