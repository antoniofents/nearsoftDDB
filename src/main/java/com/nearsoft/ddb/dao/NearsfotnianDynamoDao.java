package com.nearsoft.ddb.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.nearsoft.ddb.com.nearsoft.ddb.model.Nearsoftnian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.dsig.Manifest;
import java.util.*;

public class NearsfotnianDynamoDao implements NearsoftnianDao {

    private static final Logger logger = LoggerFactory.getLogger(NearsfotnianDynamoDao.class);

    public NearsfotnianDynamoDao(String tableName) {
        this.tableName = tableName;
    }

    private String tableName;


    @Autowired
    private AmazonDynamoDB dynamoClient;


    @Override
    public List<Nearsoftnian> getNearsoftnians() {

        ArrayList<Nearsoftnian> nearsoftnians = new ArrayList();

        ScanResult result = null;
        ScanRequest req = new ScanRequest();
        req.setTableName(tableName);

        if (result != null) {
            req.setExclusiveStartKey(result.getLastEvaluatedKey());
        }

        do {
            req.setTableName(tableName);

            if (result != null) {
                req.setExclusiveStartKey(result.getLastEvaluatedKey());
            }

            result = dynamoClient.scan(req);

            List<Map<String, AttributeValue>> rows = result.getItems();

            for (Map<String, AttributeValue> map : rows) {
                try {
                    AttributeValue technology = map.get("technology");
                    AttributeValue desknumber = map.get("desknumber");
                    nearsoftnians.add(new Nearsoftnian(map.get("id_ns").getS(), map.get("mail").getS(), technology!=null?technology.getS():null, desknumber!=null?Integer.parseInt(desknumber.getN()):0));

                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (result.getLastEvaluatedKey() != null);


        return nearsoftnians;

    }

    @Override
    public String getNearsoftnianById(String id) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("id_ns", id, "mail", id + "@nearsoft.com");
        try {
            logger.error("getting nearsoftnian");
            Item item = getTableInstance().getItem(spec);
            return item!=null?item.toJSON():null;
        } catch (Exception e) {
            logger.error("unable to search for item");
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Nearsoftnian> findNearsoftniansByEmail(String id) {
        return null;
    }

    @Override
    public boolean saveOrUpdate(Nearsoftnian nearsoftnian) {

        if (getNearsoftnianById(nearsoftnian.getId()) == null) {
            nearsoftnian.setMail(nearsoftnian.getId()+"@nearsoft.com");
            try {
                logger.error("saving item");
                getTableInstance().putItem(new Item()
                        .withPrimaryKey(getPrimaryKey(nearsoftnian))
                        .withString("technology", nearsoftnian.getTechnology())
                        .withNumber("desknumber", nearsoftnian.getDeskNumber()));
                return true;

            } catch (Exception e) {
                logger.error("unable to save item");
                return false;

            }
        } else {
            return update(nearsoftnian);
        }

    }

    @Override
    public boolean update(Nearsoftnian nearsoftnian) {


        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(getPrimaryKey(nearsoftnian))
                .withUpdateExpression("set technology = :r, desknumber=:p")
                .withValueMap(new ValueMap().withString(":r", nearsoftnian.getTechnology()).
                        withNumber(":p", nearsoftnian.getDeskNumber()))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            logger.info("Updating the item...");
            getTableInstance().updateItem(updateItemSpec);
            return true;

        } catch (Exception e) {
            logger.error("Unable to update item: " + nearsoftnian);
            return false;
        }

    }

    @Override
    public boolean delete(String key) {



        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("id_ns", key, "mail", key+"@nearsoft.com"));

        try {
            logger.info("Attempting a conditional delete...");
            getTableInstance().deleteItem(deleteItemSpec);
            logger.info("DeleteItem succeeded");
            return true;
        } catch (Exception e) {
            logger.error("Unable to delete item: ");
            logger.error(e.getMessage());
            return false;
        }

    }


    private Table getTableInstance() {
        DynamoDB dynamoDB = new DynamoDB(dynamoClient);
        return dynamoDB.getTable(tableName);

    }

    private PrimaryKey getPrimaryKey(Nearsoftnian nearsoftnian) {
        return new PrimaryKey("id_ns", nearsoftnian.getId(), "mail", nearsoftnian.getMail());

    }
}
