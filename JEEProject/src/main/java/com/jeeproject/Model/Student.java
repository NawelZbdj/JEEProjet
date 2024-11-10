package com.jeeproject.Model;
import java.util.Date;

public class Student implements IUser{
    private int id;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String email;

    public Student(){
    }

    //Getters
    @Override
    public int getId(){
        return id;
    }

    @Override
    public String getLastName(){
        return lastName;
    }

    @Override
    public String getFirstName(){
        return firstName;
    }

    @Override
    public Date getBirthDate(){
        return birthDate;
    }

    @Override
    public String getEmail(){
        return email;
    }

    //Setters
    @Override
    public void setId(int id){
        this.id = id;
    }

    @Override
    public void setLastName(String lastname){
        this.lastName = lastname;
    }

    @Override
    public void setFirstName(String firstname){
        this.firstName = firstname;
    }

    @Override
    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }

    @Override
    public void setEmail(String email){
        this.email = email;
    }
}
