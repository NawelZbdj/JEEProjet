package com.jeeproject.Model;

import java.util.Date;

public class Registration {

    private int id;
    private Date registrationDate;
    private Student student;
    private Course course;

    private Professor professor;

    public Registration(){

    }

    //Getters
    public int getId() {
        return id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Professor getProfessor() {
        return professor;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}
