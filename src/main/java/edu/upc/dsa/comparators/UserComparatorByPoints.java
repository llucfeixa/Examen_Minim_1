package edu.upc.dsa.comparators;

import edu.upc.dsa.models.UserPoints;

import java.util.Comparator;

public class UserComparatorByPoints implements Comparator<UserPoints> {
    public int compare(UserPoints userPoints1, UserPoints userPoints2) {
        return (userPoints2.getPoints() - userPoints1.getPoints());
    }
}
