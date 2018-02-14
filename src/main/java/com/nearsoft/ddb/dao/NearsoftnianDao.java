package com.nearsoft.ddb.dao;

import com.nearsoft.ddb.com.nearsoft.ddb.model.Nearsoftnian;

import java.util.List;

public interface NearsoftnianDao {


     List<Nearsoftnian> getNearsoftnians();
     Nearsoftnian getNearsoftnianById(String id);
     List <Nearsoftnian> findNearsoftniansByEmail(String id);
     boolean saveOrUpdate(Nearsoftnian nearsoftnian);
     boolean update(Nearsoftnian nearsoftnian);
     boolean delete (String key);
}
