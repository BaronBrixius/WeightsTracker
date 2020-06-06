package com.example.weightstracker;

import java.io.Serializable;

public class Exercise implements Serializable {
    private int id = -1;
    private String name;
    private double weight;          //note: kilograms
    private double increment;       //minimum weight increase (e.g. smallest plate you can add is 2.5kg)

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public void increment() {
        weight += increment;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Exercise))
            return false;
        return this.id == ((Exercise) other).id;
    }
}
