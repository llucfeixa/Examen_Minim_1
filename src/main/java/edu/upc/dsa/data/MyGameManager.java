package edu.upc.dsa.data;

import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;

import java.util.List;

public interface MyGameManager {
    public void createUser(String userId) throws ErrorIdStartGameException, UserHasGameException, UserExistingException;

    public void createGame(String gameId, String description, int level);

    public void startPartida(String gameId, String userId) throws ErrorIdStartGameException, UserHasGameException;

    public int userLevel(String userId) throws NotUserOrGameException;

    public int userPoints(String userId) throws NotUserOrGameException;

    public void nextLevel(String userId) throws NotUserOrGameException;

    public void endPartida(String userId) throws NotUserOrGameException;

    public List<User> usersByPoints(String gameId);

    public List<Partida> partidaByUser(String userId) throws NotUserOrGameException;

    public String activityByUser(String userId);

    public int numUsers();

    public int numGames();

    public User getUser(String userId);

    public Partida getPartida(String gameId);
}
