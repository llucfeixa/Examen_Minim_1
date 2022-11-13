package edu.upc.dsa.data;

import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.*;

import java.util.List;

public interface MyGameManager {
    public void createUser(String userId) throws UserExistingException;

    public void createGame(String gameId, String description, int level);

    public void startPartida(String gameId, String userId) throws ErrorIdStartGameException, UserHasGameException;

    public int userLevel(String userId) throws NotUserOrGameException;

    public int userPoints(String userId) throws NotUserOrGameException;

    public void nextLevel(int NextPoints, String userId, String date) throws NotUserOrGameException;

    public void endPartida(String userId) throws NotUserOrGameException;

    public List<UserPoints> usersByPoints(String gameId) throws NotUserOrGameException;

    public List<Partida> partidaByUser(String userId) throws NotUserOrGameException;

    public List<ListActivity> activityByUser(String userId, String gameId);

    public int numUsers();

    public int numGames();

    public User getUser(String userId);
}
