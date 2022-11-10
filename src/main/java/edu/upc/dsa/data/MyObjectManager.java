package edu.upc.dsa.data;

import edu.upc.dsa.exceptions.BuyObjectException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.MyObject;

import java.util.List;

public interface MyObjectManager {
    public void register(String name, String surname, String date, Credentials credentials) throws UserExistingException;

    public List<User> usersByAlphabet();

    public int login(Credentials credentials);

    public void addObject(String objectId, String name, String description, double coins);

    public List<MyObject> objectsByPrice();

    public void buyObject(String mail, String objectId) throws BuyObjectException;

    public List<MyObject> objectsByUser(String mail);

    public int numUsers();

    public int numObjects();

    public double getUserCoins(String mail);
}
