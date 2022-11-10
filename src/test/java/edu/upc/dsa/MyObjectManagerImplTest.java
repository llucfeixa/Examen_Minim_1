package edu.upc.dsa;

import edu.upc.dsa.data.MyObjectManager;
import edu.upc.dsa.data.MyObjectManagerImpl;
import edu.upc.dsa.exceptions.BuyObjectException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.MyObject;
import edu.upc.dsa.models.User;
import org.eclipse.persistence.jaxb.Crate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MyObjectManagerImplTest {
    MyObjectManager om;

    @Before
    public void setUp() throws UserExistingException, BuyObjectException {
        om = new MyObjectManagerImpl();
        Credentials credentials1 = new Credentials("llucfeixa@gmail.com", "1234");
        om.register("Lluc", "Feixa", "29/12/2001", credentials1);
        Credentials credentials2 = new Credentials("kevintorres@gmail.com", "efgh");
        om.register("Kevin", "Torres", "23/07/2001", credentials2);
        Credentials credentials3 = new Credentials("paufeixa20019@gmail.com", "abcd");
        om.register("Pau", "Feixa", "29/12/2001", credentials3);
        Credentials credentials4 = new Credentials("perefeixa@gmail.com", "5678");
        om.register("Pere", "Morancho", "03/11/2003", credentials4);

        om.addObject("1", "Carpeta", "Carpeta para estudiantes", 10);
        om.addObject("2", "Bolígrafo azul", "Bolígrafo de color azul", 1);
        om.addObject("3", "Bolígrafo negro", "Bolígrafo de color negro", 1.25);
        om.addObject("4", "Bolígrafo rojo", "Bolígrafo de color rojo", 1.5);
        om.addObject("5", "Mochila", "Mochila para estudiantes", 35);

        om.buyObject("llucfeixa@gmail.com", "5");
    }

    @After
    public void tearDown() {
        this.om = null;
    }

    @Test
    public void testRegister() throws UserExistingException {
        Assert.assertEquals(4, this.om.numUsers());
        Credentials credentials5 = new Credentials("rogertudel@gmail.com", "1234");
        om.register("Roger", "Tudel", "14/09/2001", credentials5);
        Assert.assertEquals(5, this.om.numUsers());
    }

    @Test
    public void testUsersByAlphabet() throws UserExistingException {
        testRegister();
        List<User> users = this.om.usersByAlphabet();

        Assert.assertEquals("Feixa", users.get(0).getSurname());
        Assert.assertEquals("Lluc", users.get(0).getName());

        Assert.assertEquals("Feixa", users.get(1).getSurname());
        Assert.assertEquals("Pau", users.get(1).getName());

        Assert.assertEquals("Morancho", users.get(2).getSurname());
        Assert.assertEquals("Pere", users.get(2).getName());

        Assert.assertEquals("Torres", users.get(3).getSurname());
        Assert.assertEquals("Kevin", users.get(3).getName());

        Assert.assertEquals("Tudel", users.get(4).getSurname());
        Assert.assertEquals("Roger", users.get(4).getName());
    }

    @Test
    public void testLogin() {
        Credentials credentials1 = new Credentials("llucfeixa@gmail.com", "1234");
        Assert.assertEquals(0, om.login(credentials1));
        Credentials credentials2 = new Credentials("llucfeixa@gmail.com", "12");
        Assert.assertEquals(-1, om.login(credentials2));
        Credentials credentials3 = new Credentials("paufeixa@gmail.com", "abcd");
        Assert.assertEquals(-1, om.login(credentials3));
        Credentials credentials4 = new Credentials("kevintorres@gmail.com", "12");
        Assert.assertEquals(-1, om.login(credentials4));
    }


    @Test
    public void testAddObject() {
        Assert.assertEquals(5, this.om.numObjects());
        om.addObject("1", "Calculadora", "Calculadora Científica", 25);
        Assert.assertEquals(5, this.om.numObjects());
        om.addObject("6", "Estuche", "Estuche escolar", 7);
        Assert.assertEquals(6, this.om.numObjects());
    }

    @Test
    public void testObjectsByPrice() {
        List<MyObject> myObjects = this.om.objectsByPrice();

        Assert.assertEquals("Mochila", myObjects.get(0).getName());
        Assert.assertEquals(35, myObjects.get(0).getCoins(), 0);

        Assert.assertEquals("Carpeta", myObjects.get(1).getName());
        Assert.assertEquals(10, myObjects.get(1).getCoins(), 0);

        Assert.assertEquals("Bolígrafo rojo", myObjects.get(2).getName());
        Assert.assertEquals(1.5, myObjects.get(2).getCoins(), 0);

        Assert.assertEquals("Bolígrafo negro", myObjects.get(3).getName());
        Assert.assertEquals(1.25, myObjects.get(3).getCoins(), 0);

        Assert.assertEquals("Bolígrafo azul", myObjects.get(4).getName());
        Assert.assertEquals(1, myObjects.get(4).getCoins(), 0);
    }

    @Test
    public void testBuyObject() throws BuyObjectException {
        Assert.assertEquals(15, this.om.getUserCoins("llucfeixa@gmail.com"), 0);

        this.om.buyObject("llucfeixa@gmail.com", "1");
        Assert.assertEquals(5, this.om.getUserCoins("llucfeixa@gmail.com"), 0);

        this.om.buyObject("llucfeixa@gmail.com", "2");
        Assert.assertEquals(4, this.om.getUserCoins("llucfeixa@gmail.com"), 0);

        this.om.buyObject("llucfeixa@gmail.com", "2");
        Assert.assertEquals(3, this.om.getUserCoins("llucfeixa@gmail.com"), 0);

        Assert.assertEquals(50, this.om.getUserCoins("paufeixa20019@gmail.com"), 0);
    }

    @Test
    public void testObjectsByUser() throws BuyObjectException {
        Assert.assertEquals(1, this.om.objectsByUser("llucfeixa@gmail.com").size());

        this.om.buyObject("llucfeixa@gmail.com", "1");
        Assert.assertEquals(2, this.om.objectsByUser("llucfeixa@gmail.com").size());

        this.om.buyObject("llucfeixa@gmail.com", "2");
        Assert.assertEquals(3, this.om.objectsByUser("llucfeixa@gmail.com").size());

        this.om.buyObject("llucfeixa@gmail.com", "2");
        Assert.assertEquals(4, this.om.objectsByUser("llucfeixa@gmail.com").size());

        Assert.assertEquals(0, this.om.objectsByUser("paufeixa20019@gmail.com").size());
    }
}
