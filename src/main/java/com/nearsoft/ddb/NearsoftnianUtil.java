package com.nearsoft.ddb;

import com.google.gson.Gson;
import com.nearsoft.ddb.com.nearsoft.ddb.model.Nearsoftnian;

public class NearsoftnianUtil {

    public static  Nearsoftnian getNearsoftnianFromJson(String json){
        return new Gson().fromJson(json, Nearsoftnian.class);
    }

    public static String getNearsoftnianEmail(String id){
        return id+"@nearsoft.com";
    }
}
