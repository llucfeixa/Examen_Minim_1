package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    String userId;
    Partida partida;
    List<Partida> partidas;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
        this.partidas = new ArrayList<>();
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Partida getPartida() {
        return this.partida;
    }

    public void setPartidaActual(Partida partida) {
        this.partida = partida;
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
