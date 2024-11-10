package com.jeeproject.Model;

import java.util.Date;

public class Administrator implements IUser{

    private int id;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String position;
    private String email;


    public Administrator(){

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

    public String getPosition() {
        return position;
    }

    @Override
    public String getEmail() {
        return email;
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

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }


}
