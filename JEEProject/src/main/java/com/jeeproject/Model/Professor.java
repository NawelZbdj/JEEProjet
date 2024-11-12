package com.jeeproject.Model;

import java.util.Date;

public class Professor implements IUser {

    private int id;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String specialty;
    private String email;
    private Account account;

    public Professor(){

    }


    //Getters
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public Date getBirthDate() {
        return birthDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String getEmail() {
        return email;
    }
    public Account getAccount() {
        return account;
    }


    //Setters


    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

}
