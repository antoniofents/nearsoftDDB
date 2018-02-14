package com.nearsoft.ddb.controller;

import com.nearsoft.ddb.com.nearsoft.ddb.model.Nearsoftnian;
import com.nearsoft.ddb.dao.NearsoftnianDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nearsoftnian")
public class NearsoftniansController {


    @Autowired
    NearsoftnianDao nearsoftnianDao;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getNearsoftnians() {
        return "nearsoftnians";
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String getNearsoftnianById(@PathVariable String id) {

        return nearsoftnianDao.getNearsoftnianById(id);
    }

    @RequestMapping(value = "/getByMail/{email}", method = RequestMethod.GET)
    public String getNearsoftnianByEmail(@PathVariable String email) {
        return "nearsoftnians";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteNearsoftnian(@PathVariable String id) {
         return nearsoftnianDao.delete(id)?"SUCCESS":"ERROR";
    }

    @PostMapping(value = "/save/")
    public String saveOrUpdateNearsoftnian(@RequestBody String nearsoftnian) {
        //post must receive a value not a field
        return nearsoftnianDao.saveOrUpdate(new Nearsoftnian(nearsoftnian))?"SUCCESS":"ERROR";
    }


}
