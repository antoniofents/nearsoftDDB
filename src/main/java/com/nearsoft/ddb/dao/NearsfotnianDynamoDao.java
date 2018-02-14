package com.nearsoft.ddb.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.nearsoft.ddb.com.nearsoft.ddb.model.Nearsoftnian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

        return null;
    }

    @Override
    public String getNearsoftnianById(String id) {


        GetItemSpec spec = new GetItemSpec().withPrimaryKey("id_ns", id, "mail", id + "@nearsoft.com");
        try {
            logger.error("getting nearsoftnian");
            return getTableInstance().getItem(spec).toJSON();
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
                .withPrimaryKey(new PrimaryKey("id_ns", key));

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
