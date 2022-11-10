package edu.upc.dsa.data;

import edu.upc.dsa.comparators.MyObjectComparatorByPrice;
import edu.upc.dsa.comparators.UserComparatorByAlphabet;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.BuyObjectException;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.MyObject;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyObjectManagerImpl implements MyObjectManager {
    private static MyObjectManager instance;
    final static Logger logger = Logger.getLogger(MyObjectManagerImpl.class);
    HashMap<String, User> users;
    HashMap<String, MyObject> objects;

    public MyObjectManagerImpl() {
        users = new HashMap<>();
        objects = new HashMap<>();
    }

    @Override
    public void register(String name, String surname, String date, Credentials credentials) throws UserExistingException {
        if (users.containsKey(credentials.getMail())) {
            throw new UserExistingException();
        }
        User newUser = new User(name, surname, date, credentials);
        users.put(credentials.getMail(), newUser);
    }

    @Override
    public List<User> usersByAlphabet() {
        List<User> usersByAlphabet = new ArrayList<>(users.values());
        usersByAlphabet.sort(new UserComparatorByAlphabet());
        return usersByAlphabet;
    }

    @Override
    public int login(Credentials credentials) {
        if (users.containsKey(credentials.getMail())) {
            User user = users.get(credentials.getMail());
            if (user.getCredentials().getPassword().equals(credentials.getPassword())) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public void addObject(String objectId, String name, String description, double coins) {
        MyObject newMyObject = new MyObject(objectId, name, description, coins);
        objects.put(objectId, newMyObject);
    }

    @Override
    public List<MyObject> objectsByPrice() {
        List<MyObject> objectsByPrice = new ArrayList<>(objects.values());
        objectsByPrice.sort(new MyObjectComparatorByPrice());
        return objectsByPrice;
    }

    @Override
    public void buyObject(String mail, String objectId) throws BuyObjectException {
        if (!users.containsKey(mail) || !objects.containsKey(objectId)) {
            throw new BuyObjectException();
        }
        User user = users.get(mail);
        MyObject myObject = objects.get(objectId);
        if (user.getCoins() < myObject.getCoins()) {
            throw new BuyObjectException();
        }
        user.addObject(myObject);
    }

    @Override
    public List<MyObject> objectsByUser(String mail) {
        return users.get(mail).getObjects();
    }

    @Override
    public int numUsers() {
        return users.size();
    }

    @Override
    public int numObjects() {
        return objects.size();
    }

    @Override
    public double getUserCoins(String mail) {
        return users.get(mail).getCoins();
    }

    public static MyObjectManager getInstance() {
        if (instance==null) {
            instance = new MyObjectManagerImpl();
        }
        return instance;
    }
}
