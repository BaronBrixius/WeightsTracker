package com.example.weightstracker;

import android.text.Editable;

import java.io.Serializable;

public class Exercise implements Serializable {
    private int id;
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

    public void setName(Editable name) {
        this.name = name.toString();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(Editable weight) {
        this.weight = Double.parseDouble(weight.toString());
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(Editable increment) {
        this.increment = Double.parseDouble(increment.toString());
    }

    public double increment() {
        return weight += increment;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Exercise))
            return false;
        return this.id == ((Exercise) other).id;
    }
}
