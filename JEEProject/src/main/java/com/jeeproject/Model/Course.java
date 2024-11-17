package com.jeeproject.Model;

public class Course {
    private int id;
    private String title;
    private String description;
    private double credit;
    private String speciality;


    public Course(){

    }



    //Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getCredit() {
        return credit;
    }

    public String getSpeciality(){ return speciality;}


    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setSpeciality(String speciality) {this.speciality = speciality;}
}
