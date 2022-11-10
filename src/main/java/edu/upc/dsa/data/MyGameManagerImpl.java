package edu.upc.dsa.data;

import edu.upc.dsa.comparators.UserComparatorByPoints;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyGameManagerImpl implements MyGameManager {
    private static MyGameManager instance;
    final static Logger logger = Logger.getLogger(MyGameManagerImpl.class);
    HashMap<String, User> users;
    HashMap<String, Game> games;
    HashMap<String, Partida> partidas;

    public MyGameManagerImpl() {
        users = new HashMap<>();
        games = new HashMap<>();
        partidas = new HashMap<>();
    }

    @Override
    public void createUser(String userId) throws UserExistingException {
        if (users.containsKey(userId)) {
            throw new UserExistingException();
        }
        User newUser = new User(userId);
        users.put(userId, newUser);
    }

    @Override
    public void createGame(String gameId, String description, int levels) {
        if (!games.containsKey(gameId)) {
            Game newGame = new Game(gameId, description, levels);
            games.put(gameId, newGame);
        }
    }

    @Override
    public void startPartida(String gameId, String userId) throws ErrorIdStartGameException, UserHasGameException {
        if (!users.containsKey(userId) || !games.containsKey(gameId)) {
            throw new ErrorIdStartGameException();
        }
        if (users.get(userId).getGameId() != null) {
            throw new UserHasGameException();
        } else {
            User user = users.get(userId);
            user.setGameId(gameId);
            user.setPoints(50);
            user.setLevels(1);
            if (partidas.containsKey(gameId)) {
                partidas.get(gameId).setUser(user);
                user.setPartida(partidas.get(gameId));
            } else {
                Partida newPartida = new Partida(gameId, user);
                user.setPartida(newPartida);
                partidas.put(gameId, newPartida);
            }
        }
    }

    @Override
    public int userLevel(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            throw new NotUserOrGameException();
        } else if (users.get(userId).getGameId() == null) {
            throw new NotUserOrGameException();
        }
        User userLevel = users.get(userId);
        return userLevel.getLevels();
    }

    @Override
    public int userPoints(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            throw new NotUserOrGameException();
        } else if (users.get(userId).getGameId() == null) {
            throw new NotUserOrGameException();
        }
        User userPoints = users.get(userId);
        return userPoints.getPoints();
    }

    @Override
    public void nextLevel(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            throw new NotUserOrGameException();
        }
        int levels = users.get(userId).getLevels();
        users.get(userId).setLevels(levels + 1);
        String gameId = users.get(userId).getGameId();
        int levelsGame = games.get(gameId).getLevels();
        if (levels + 1 == levelsGame) {
            int points = users.get(userId).getPoints();
            users.get(userId).setPoints(points + 100);
        }
    }

    @Override
    public void endPartida(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            throw new NotUserOrGameException();
        } else if (users.get(userId).getGameId() == null) {
            throw new NotUserOrGameException();
        }
        users.get(userId).setGameId(null);
    }

    @Override
    public List<User> usersByPoints(String gameId) {
        List<User> usersByPoints = partidas.get(gameId).getUsers();
        usersByPoints.sort(new UserComparatorByPoints());
        return usersByPoints;
    }

    @Override
    public List<Partida> partidaByUser(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            throw new NotUserOrGameException();
        }
        return users.get(userId).getPartidas();
    }

    @Override
    public String activityByUser(String userId) {
        return null;
    }

    @Override
    public int numUsers() {
        return users.size();
    }

    @Override
    public int numGames() {
        return games.size();
    }

    @Override
    public User getUser(String userId) {
        return users.get(userId);
    }

    @Override
    public Partida getPartida(String gameId) {
        return partidas.get(gameId);
    }

    public static MyGameManager getInstance() {
        if (instance == null) {
            instance = new MyGameManagerImpl();
        }
        return instance;
    }
}
