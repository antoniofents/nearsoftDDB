package com.nearsoft.ddb.com.nearsoft.ddb.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Nearsoftnian {


    public Nearsoftnian() {

    }

    public Nearsoftnian(String id, String mail, String technology, int desknumber) {
        this.id = id;
        this.mail = mail;
        this.technology = technology;
        this.deskNumber = desknumber;
    }

    @SerializedName(value = "id_ns", alternate = {"id"})
    private String id;
    private String mail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(int deskNumber) {
        this.deskNumber = deskNumber;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    private int deskNumber;
    private String technology;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
