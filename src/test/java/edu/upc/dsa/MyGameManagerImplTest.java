package edu.upc.dsa;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MyGameManagerImplTest {
    MyGameManager gm;

    @Before
    public void setUp() throws UserExistingException, ErrorIdStartGameException, UserHasGameException {
        gm = new MyGameManagerImpl();
        gm.createUser("Lluc");
        gm.createUser("Marc");
        gm.createUser("Jordi");
        gm.createUser("Maria");

        gm.createGame("1", "Partida uno", 3);
        gm.createGame("2", "Partida dos", 5);
        gm.createGame("3", "Partida tres", 7);
        gm.createGame("4", "Partida cuatro", 10);
    }

    @After
    public void tearDown() {
        this.gm = null;
    }

    @Test
    public void testCreateUser() throws UserExistingException, ErrorIdStartGameException, UserHasGameException {
        Assert.assertEquals(4, this.gm.numUsers());
        gm.createUser("Oriol");
        Assert.assertEquals(5, this.gm.numUsers());
    }

    @Test
    public void testCreateGame() {
        Assert.assertEquals(4, this.gm.numGames());
        gm.createGame("5", "Partida cinco", 15);
        Assert.assertEquals(5, this.gm.numGames());
    }

    @Test
    public void testStartPartida() throws ErrorIdStartGameException, UserHasGameException {
        this.gm.startPartida("1", "Lluc");
        Assert.assertEquals("1", this.gm.getUser("Lluc").getGameId());
        Assert.assertEquals(50, this.gm.getUser("Lluc").getPoints());
        Assert.assertEquals(1, this.gm.getUser("Lluc").getLevels());
        this.gm.startPartida("1", "Marc");
        Assert.assertEquals(2, this.gm.getPartida("1").getUsers().size());
    }

    @Test
    public void testUserLevel() throws ErrorIdStartGameException, UserHasGameException {
        testStartPartida();

        Assert.assertEquals(1, this.gm.getUser("Lluc").getLevels());

        this.gm.getUser("Lluc").setLevels(2);
        Assert.assertEquals(2, this.gm.getUser("Lluc").getLevels());
    }

    @Test
    public void testUserPoints() throws ErrorIdStartGameException, UserHasGameException {
        testStartPartida();

        Assert.assertEquals(50, this.gm.getUser("Lluc").getPoints());

        this.gm.getUser("Lluc").setPoints(100);
        Assert.assertEquals(100, this.gm.getUser("Lluc").getPoints());
    }

    @Test
    public void testNextLevel() throws ErrorIdStartGameException, UserHasGameException, NotUserOrGameException {
        testStartPartida();

        Assert.assertEquals(1, this.gm.getUser("Lluc").getLevels());

        this.gm.nextLevel("Lluc");
        Assert.assertEquals(2, this.gm.getUser("Lluc").getLevels());
        Assert.assertEquals(50, this.gm.getUser("Lluc").getPoints());

        this.gm.nextLevel("Lluc");
        Assert.assertEquals(3, this.gm.getUser("Lluc").getLevels());
        Assert.assertEquals(150, this.gm.getUser("Lluc").getPoints());
    }

    @Test
    public void testEndPartida() throws NotUserOrGameException, ErrorIdStartGameException, UserHasGameException {
        testStartPartida();

        Assert.assertEquals("1", this.gm.getUser("Lluc").getGameId());
        this.gm.endPartida("Lluc");
        Assert.assertEquals(null, this.gm.getUser("Lluc").getGameId());
    }

    @Test
    public void testUsersByPoints() throws ErrorIdStartGameException, UserHasGameException {
        testStartPartida();

        this.gm.getUser("Lluc").setPoints(100);

        List<User> usersByPoints = this.gm.usersByPoints("1");

        Assert.assertEquals("Lluc", usersByPoints.get(0).getUserId());
        Assert.assertEquals(100, usersByPoints.get(0).getPoints(), 0);

        Assert.assertEquals("Marc", usersByPoints.get(1).getUserId());
        Assert.assertEquals(50, usersByPoints.get(1).getPoints(), 0);

        this.gm.getUser("Marc").setPoints(200);

        usersByPoints = this.gm.usersByPoints("1");

        Assert.assertEquals("Marc", usersByPoints.get(0).getUserId());
        Assert.assertEquals(200, usersByPoints.get(0).getPoints(), 0);

        Assert.assertEquals("Lluc", usersByPoints.get(1).getUserId());
        Assert.assertEquals(100, usersByPoints.get(1).getPoints(), 0);
    }

    @Test
    public void testPartidaByUsers() throws ErrorIdStartGameException, UserHasGameException {
        testStartPartida();

        Assert.assertEquals(1, this.gm.getUser("Lluc").getPartidas().size());
    }

    @Test
    public void testActivityByUser() {

    }
}
