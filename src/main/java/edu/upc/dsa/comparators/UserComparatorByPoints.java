package edu.upc.dsa.comparators;

import edu.upc.dsa.models.User;

import java.util.Comparator;

public class UserComparatorByPoints implements Comparator<User> {
    public int compare(User user1, User user2) {
        return (user2.getPoints() - user1.getPoints());
    }
}
