package com.jeeproject.Model;

public class Result {
    private int id;
    private double grade;
    private double coefficient;
    private Registration registration;


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

    public Registration getRegistration() {
        return registration;
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

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

}
