package edu.upc.dsa;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.UserPoints;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MyGameManagerImplTest {
    MyGameManager gm;

    @Before
    public void setUp() throws UserExistingException {
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
    public void testCreateUser() throws UserExistingException {
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
        Assert.assertEquals("1", this.gm.getUser("Lluc").getPartida().getGameId());
        Assert.assertEquals(50, this.gm.getUser("Lluc").getPartida().getPoints());
        Assert.assertEquals(1, this.gm.getUser("Lluc").getPartida().getLevels());
        this.gm.startPartida("3", "Marc");
        Assert.assertEquals("3", this.gm.getUser("Marc").getPartida().getGameId());
        Assert.assertEquals(50, this.gm.getUser("Marc").getPartida().getPoints());
        Assert.assertEquals(1, this.gm.getUser("Marc").getPartida().getLevels());
        this.gm.startPartida("1", "Jordi");
    }

    @Test
    public void testUserLevel() throws ErrorIdStartGameException, UserHasGameException, NotUserOrGameException {
        testStartPartida();

        Assert.assertEquals(1, this.gm.userLevel("Lluc"));

        this.gm.getUser("Lluc").getPartida().setLevels(2);
        Assert.assertEquals(2, this.gm.userLevel("Lluc"));
    }

    @Test
    public void testUserPoints() throws ErrorIdStartGameException, UserHasGameException, NotUserOrGameException {
        testStartPartida();

        Assert.assertEquals(50, this.gm.userPoints("Lluc"));

        this.gm.getUser("Lluc").getPartida().setPoints(100);
        Assert.assertEquals(100, this.gm.userPoints("Lluc"));
    }

    @Test
    public void testNextLevel() throws ErrorIdStartGameException, UserHasGameException, NotUserOrGameException {
        testStartPartida();

        Assert.assertEquals(1, this.gm.getUser("Lluc").getPartida().getLevels());

        this.gm.nextLevel(20,"Lluc", "12-11-2022");
        Assert.assertEquals(2, this.gm.getUser("Lluc").getPartida().getLevels());
        Assert.assertEquals(70, this.gm.getUser("Lluc").getPartida().getPoints());

        this.gm.nextLevel(10, "Lluc", "13-11-2022");
        Assert.assertEquals(3, this.gm.getUser("Lluc").getPartida().getLevels());
        Assert.assertEquals(80, this.gm.getUser("Lluc").getPartida().getPoints());
    }

    @Test
    public void testEndPartida() throws NotUserOrGameException, ErrorIdStartGameException, UserHasGameException {
        testStartPartida();

        Assert.assertEquals("1", this.gm.getUser("Lluc").getPartida().getGameId());
        this.gm.endPartida("Lluc");
        Assert.assertNull(this.gm.getUser("Lluc").getPartida());
    }

    @Test
    public void testUsersByPoints() throws ErrorIdStartGameException, UserHasGameException, NotUserOrGameException {
        testStartPartida();

        this.gm.nextLevel(20, "Lluc", "13-11-2022");

        List<UserPoints> usersByPoints = this.gm.usersByPoints("1");

        Assert.assertEquals("Lluc", usersByPoints.get(0).getUserId());
        Assert.assertEquals(70, usersByPoints.get(0).getPoints(), 0);

        Assert.assertEquals("Jordi", usersByPoints.get(1).getUserId());
        Assert.assertEquals(50, usersByPoints.get(1).getPoints(), 0);

        this.gm.nextLevel(50, "Jordi", "13-11-2022");

        usersByPoints = this.gm.usersByPoints("1");

        Assert.assertEquals("Jordi", usersByPoints.get(0).getUserId());
        Assert.assertEquals(100, usersByPoints.get(0).getPoints(), 0);

        Assert.assertEquals("Lluc", usersByPoints.get(1).getUserId());
        Assert.assertEquals(70, usersByPoints.get(1).getPoints(), 0);
    }

    @Test
    public void testPartidaByUsers() throws ErrorIdStartGameException, UserHasGameException, NotUserOrGameException {
        testStartPartida();

        this.gm.endPartida("Lluc");
        Assert.assertEquals(1, this.gm.getUser("Lluc").getPartidas().size());

        this.gm.startPartida("2", "Lluc");
        this.gm.endPartida("Lluc");
        Assert.assertEquals(2, this.gm.getUser("Lluc").getPartidas().size());
    }

    @Test
    public void testActivityByUser() throws ErrorIdStartGameException, UserHasGameException, NotUserOrGameException {
        testStartPartida();

        this.gm.nextLevel(20,"Lluc", "12-11-2022");
        this.gm.endPartida("Lluc");

        Assert.assertEquals(1, this.gm.activityByUser("Lluc", "1").size());
        Assert.assertEquals(3, this.gm.activityByUser("Lluc", "1").get(0).getActivities().size());

        this.gm.startPartida("1", "Lluc");
        this.gm.nextLevel(40,"Lluc", "13-11-2022");
        this.gm.nextLevel(30,"Lluc", "13-11-2022");
        this.gm.endPartida("Lluc");

        Assert.assertEquals(2, this.gm.activityByUser("Lluc", "1").size());
        Assert.assertEquals(4, this.gm.activityByUser("Lluc", "1").get(1).getActivities().size());
    }
}
