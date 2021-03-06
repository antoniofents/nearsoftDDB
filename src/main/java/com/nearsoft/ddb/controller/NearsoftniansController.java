package com.nearsoft.ddb.controller;

import com.nearsoft.ddb.NearsoftnianUtil;
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
        return nearsoftnianDao.getNearsoftnians().toString();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String getNearsoftnianById(@PathVariable String id) {
        Nearsoftnian nearsoftnian = nearsoftnianDao.getNearsoftnianById(id);
        return nearsoftnian!=null?nearsoftnian.toString():"no results";
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
        return nearsoftnianDao.saveOrUpdate(NearsoftnianUtil.getNearsoftnianFromJson(nearsoftnian))?"SUCCESS":"ERROR";
    }


}
