package edu.upc.dsa.models;

public class GameCreate {
    String gameCreateId;
    String description;
    int levels;

    public GameCreate() {
    }

    public GameCreate(String gameCreateId, String description, int levels) {
        this.gameCreateId = gameCreateId;
        this.description = description;
        this.levels = levels;
    }

    public String getGameCreateId() {
        return this.gameCreateId;
    }

    public void setGameCreateId(String gameCreateId) {
        this.gameCreateId = gameCreateId;
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
