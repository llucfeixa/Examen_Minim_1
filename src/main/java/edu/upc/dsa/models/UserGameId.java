package edu.upc.dsa.models;

public class UserGameId {
    String userId;
    String gameId;

    public UserGameId() {
    }

    public UserGameId(String userId, String gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
