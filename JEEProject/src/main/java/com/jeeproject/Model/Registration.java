package com.jeeproject.Model;

import java.util.Date;

public class Registration {

    private int id;
    private Date registrationDate;


    public Registration(){

    }



    //Getters
    public int getId() {
        return id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }


    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }


}
