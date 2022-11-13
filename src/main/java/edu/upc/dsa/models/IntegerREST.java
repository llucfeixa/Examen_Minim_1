package edu.upc.dsa.models;

import io.swagger.models.auth.In;

public class IntegerREST {
    int integer;

    public IntegerREST() {
    }

    public IntegerREST(int integer) {
        this.integer = integer;
    }

    public int getIntegerREST() {
        return this.integer;
    }

    public void setIntegerREST(int integer) {
        this.integer = integer;
    }
}
