package com.nearsoft.ddb.com.nearsoft.ddb.model;

public class Nearsoftnian {

    public Nearsoftnian(String json){
        //todo unmarshall
    }

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




}
