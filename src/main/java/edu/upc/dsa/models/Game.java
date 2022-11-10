package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Game {
    String gameId;
    String description;
    int levels;

    public Game() {
    }

    public Game(String gameId, String description, int levels) {
        this.gameId = gameId;
        this.description = description;
        this.levels = levels;
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
}
