package com.jeeproject.Model;

public class Result {
    private int id;
    private double grade;
    private double coefficient;


    public Result(){

    }


    //Getters
    public int getId() {
        return id;
    }

    public double getGrade() {
        return grade;
    }

    public double getCoefficient() {
        return coefficient;
    }


    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
