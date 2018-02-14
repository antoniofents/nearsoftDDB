package com.nearsoft.ddb.com.nearsoft.ddb.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Nearsoftnian {


    public Nearsoftnian(String json) {
        JsonObject ns = new Gson().fromJson(json, JsonObject.class);

        this.id = ns.get("id_ns").getAsString();
        this.mail = ns.get("mail").getAsString();
        ;
        JsonElement desknumber = ns.get("desknumber");
        this.deskNumber = desknumber != null ? desknumber.getAsInt() : 0;
        JsonElement technology = ns.get("technology");
        this.technology = technology != null ? technology.getAsString() : null;
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


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
