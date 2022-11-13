package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    String gameId;
    String description;
    int levels;
    List<UserPoints> userPoints;

    public Game() {
    }

    public Game(String gameId, String description, int levels) {
        this.gameId = gameId;
        this.description = description;
        this.levels = levels;
        this.userPoints = new ArrayList<>();
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevels() {
        return this.levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public List<UserPoints> getUserPoints() {
        return this.userPoints;
    }

    public void setUserPointsList(List<UserPoints> userPoints) {
        this.userPoints = userPoints;
    }

    public void setUserPoints(UserPoints userPoints) {
        this.userPoints.add(userPoints);
    }

    public void updateUserPoints(UserPoints userPoints) {
        int i = this.userPoints.size() - 1;
        boolean found = false;
        while (i >= 0 && !found) {
            if (Objects.equals(this.userPoints.get(i).getUserId(), userPoints.userId)) {
                this.userPoints.set(i, userPoints);
                found = true;
            }
            i = i - 1;
        }
    }
}
