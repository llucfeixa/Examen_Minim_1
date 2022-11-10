package edu.upc.dsa.comparators;

import edu.upc.dsa.models.User;

import java.util.Comparator;

public class UserComparatorByAlphabet implements Comparator<User> {
    public int compare(User user1, User user2) {
        if (user1.getSurname().compareTo(user2.getSurname()) == 0) {
            return (user1.getName().compareTo(user2.getName()));
        } else {
            return (user1.getSurname().compareTo(user2.getSurname()));
        }
    }
}
