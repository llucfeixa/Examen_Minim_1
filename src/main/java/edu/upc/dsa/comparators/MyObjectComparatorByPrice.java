package edu.upc.dsa.comparators;

import edu.upc.dsa.models.MyObject;

import java.util.Comparator;

public class MyObjectComparatorByPrice implements Comparator<MyObject> {
    public int compare(MyObject myObject1, MyObject myObject2) {
        return (int) ((myObject2.getCoins() - myObject1.getCoins()) * 1000);
    }
}
