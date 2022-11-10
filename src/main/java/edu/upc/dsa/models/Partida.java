package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Partida {
    String gameId;
    List<User> users = new LinkedList<>();

    public Partida() {
    }

    public Partida(String gameId, User user) {
        this.gameId = gameId;
        this.users.add(user);
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setUser(User user) {
        this.users.add(user);
    }
}
