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
import java.util.Objects;

public class MyGameManagerImpl implements MyGameManager {
    private static MyGameManager instance;
    final static Logger logger = Logger.getLogger(MyGameManagerImpl.class);
    HashMap<String, User> users;
    HashMap<String, Game> games;

    public MyGameManagerImpl() {
        users = new HashMap<>();
        games = new HashMap<>();
    }

    @Override
    public void createUser(String userId) throws UserExistingException {
        if (users.containsKey(userId)) {
            logger.warn("User already exists");
            throw new UserExistingException();
        }
        User newUser = new User(userId);
        users.put(userId, newUser);
        logger.info("User " + userId + " created correctly");
    }

    @Override
    public void createGame(String gameId, String description, int levels) {
        Game newGame = new Game(gameId, description, levels);
        games.put(gameId, newGame);
        logger.info("Game " + gameId + " created correctly with " + levels + " levels");
    }

    @Override
    public void startPartida(String gameId, String userId) throws ErrorIdStartGameException, UserHasGameException {
        if (!users.containsKey(userId) || !games.containsKey(gameId)) {
            logger.warn("Error in the id of the player or game");
            throw new ErrorIdStartGameException();
        }
        if (users.get(userId).getPartida() != null) {
            logger.warn("User is already playing");
            throw new UserHasGameException();
        }
        Partida partida = new Partida(gameId);
        users.get(userId).setPartidaActual(partida);
        UserPoints userPoints = new UserPoints(userId, partida.getPoints());
        games.get(gameId).setUserPoints(userPoints);
        Activity activity = new Activity(partida.getLevels(), partida.getPoints(), partida.getDate());
        users.get(userId).getPartida().setActivity(activity);
        logger.info("User " + userId + " started a game with id " + gameId);
    }

    @Override
    public int userLevel(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            logger.warn("User doesn't exist");
            throw new NotUserOrGameException();
        } else if (users.get(userId).getPartida() == null) {
            logger.warn("User is not playing");
            throw new NotUserOrGameException();
        }
        int levels = users.get(userId).getPartida().getLevels();
        String gameId = users.get(userId).getPartida().getGameId();
        logger.info("User " + userId + " is in level " + levels + " in the game " + gameId);
        return levels;
    }

    @Override
    public int userPoints(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            logger.warn("User doesn't exist");
            throw new NotUserOrGameException();
        } else if (users.get(userId).getPartida() == null) {
            logger.warn("User is not playing");
            throw new NotUserOrGameException();
        }
        int points = users.get(userId).getPartida().getPoints();
        String gameId = users.get(userId).getPartida().getGameId();
        logger.info("User " + userId + " has " + points + " points in the game " + gameId);
        return users.get(userId).getPartida().getPoints();
    }

    @Override
    public void nextLevel(int nextPoints, String userId, String date) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            logger.warn("User doesn't exist");
            throw new NotUserOrGameException();
        }
        User user = users.get(userId);
        Partida partida = user.getPartida();
        Game game = games.get(partida.getGameId());
        if (partida.getLevels() == game.getLevels()) {
            int points = partida.getPoints();
            partida.setPoints(points + nextPoints + 100);
            partida.setDate(date);
            Activity activity = new Activity(partida.getLevels(), partida.getPoints(), partida.getDate());
            user.getPartida().setActivity(activity);
            UserPoints userPoints = new UserPoints(userId, user.getPartida().getPoints());
            games.get(user.getPartida().getGameId()).updateUserPoints(userPoints);
            endPartida(userId);
            logger.info("User " + userId + " ended after the last level the game " + user.getPartida().getGameId());
        }
        else {
            int levels = partida.getLevels();
            partida.setLevels(levels + 1);
            int points = partida.getPoints();
            partida.setPoints(points + nextPoints);
            partida.setDate(date);
            Activity activity = new Activity(partida.getLevels(), partida.getPoints(), partida.getDate());
            user.getPartida().setActivity(activity);
            UserPoints userPoints = new UserPoints(userId, user.getPartida().getPoints());
            games.get(user.getPartida().getGameId()).updateUserPoints(userPoints);
            logger.info("User " + userId + " advanced to the next level of game " + user.getPartida().getGameId());
        }
    }

    @Override
    public void endPartida(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            logger.warn("User doesn't exist");
            throw new NotUserOrGameException();
        } else if (users.get(userId).getPartida().getGameId() == null) {
            logger.warn("User is not playing");
            throw new NotUserOrGameException();
        }
        Partida partida = users.get(userId).getPartida();
        Activity activity = new Activity(partida.getLevels(), partida.getPoints(), partida.getDate());
        users.get(userId).getPartida().setActivity(activity);
        users.get(userId).setPartida(users.get(userId).getPartida());
        users.get(userId).setPartidaActual(null);
        logger.info("User " + userId + " ended a Partida");
    }

    @Override
    public List<UserPoints> usersByPoints(String gameId) throws NotUserOrGameException {
        if (!games.containsKey(gameId)) {
            logger.warn("User doesn't exist");
            throw new NotUserOrGameException();
        }
        List<UserPoints> usersByPoints = games.get(gameId).getUserPoints();
        usersByPoints.sort(new UserComparatorByPoints());
        logger.info("Users ordered by points in game " + gameId);
        return usersByPoints;
    }

    @Override
    public List<Partida> partidaByUser(String userId) throws NotUserOrGameException {
        if (!users.containsKey(userId)) {
            logger.warn("User doesn't exist");
            throw new NotUserOrGameException();
        }
        logger.info("List of Partidas of User " + userId);
        return users.get(userId).getPartidas();
    }

    @Override
    public List<ListActivity> activityByUser(String userId, String gameId) {
        List<ListActivity> activities = new ArrayList<>();
        int i = 0;
        List<Partida> partidas = users.get(userId).getPartidas();
        while (i < partidas.size()) {
            if (Objects.equals(partidas.get(i).getGameId(), gameId)) {
                ListActivity activity = new ListActivity(partidas.get(i).getActivities());
                activities.add(activity);
            }
            i = i + 1;
        }
        logger.info("List of Activities of User " + userId);
        return activities;
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

    public static MyGameManager getInstance() {
        if (instance == null) {
            instance = new MyGameManagerImpl();
        }
        return instance;
    }
}
