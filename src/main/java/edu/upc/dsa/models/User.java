package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class User {
    String userId;
    String gameId;
    int points;
    int levels;
    List<Partida> partidas = new LinkedList<>();

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
        this.gameId = null;
        this.levels = 0;
        this.points = 0;
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

    public  List<Partida> getPartidas() {
        return this.partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public void setPartida(Partida partida) {
        this.partidas.add(partida);
    }
}
