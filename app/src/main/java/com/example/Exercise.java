package com.example;

import android.os.Parcel;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private double weight;          //note: kilograms
    private double increment;

    public Exercise() {
        this("New Exercise", 0);
    }

    public Exercise(String name, double weight) {
        this(name, weight, 5);
    }

    public Exercise(String name, double weight, double increment) {
        setName(name);
        setWeight(weight);
        setUnit(increment);
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        weight = in.readDouble();
        increment = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public double increment() {
        return weight += increment;
    }

    public double decrement() {
        return weight -= increment;
    }

    public double getWeight() {
        return weight;
    }

    public double setWeight(double weight) {
        return this.weight = weight;
    }

    public double getIncrement() {
        return increment;
    }

    public double setUnit(double increment) {
        return this.increment = increment;
    }
}
