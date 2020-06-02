package com.example;

public class Exercise {
    private String name;
    private double weight;          //note: kilograms
    private double unit;

    public Exercise(String name, double weight){
        this(name, weight, 5);
    }

    public Exercise(String name, double weight, double unit){
        setName(name);
        setWeight(weight);
        setUnit(unit);
    }

    public String getName(){
        return name;
    }

    public String setName(String name){
        return this.name = name;
    }

    public double increment(){
        return weight += unit;
    }

    public double decrement(){
        return weight -= unit;
    }

    public double getWeight() {
        return weight;
    }

    public double setWeight(double weight){
        return this.weight = weight;
    }

    public double getUnit() {
        return unit;
    }

    public double setUnit(double unit) {
        return this.unit = unit;
    }
}
