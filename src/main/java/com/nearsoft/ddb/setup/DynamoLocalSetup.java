package com.nearsoft.ddb.setup;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

public class DynamoLocalSetup {


    static String tableName = "nearsoftnians";

    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new ProfileCredentialsProvider())
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);


    public static void main(String[] args) throws Exception {

        installTable();
    }


    static void installTable() {

        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("id_ns", KeyType.HASH), // Partition
                            new KeySchemaElement("mail", KeyType.RANGE)), // Sort key
                    Arrays.asList(new AttributeDefinition("id_ns", ScalarAttributeType.S),
                            new AttributeDefinition("mail", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }


        Table table = dynamoDB.getTable(tableName);
        try {
            table.putItem(new Item().withPrimaryKey("id_ns", "afentanes", "mail", "afentanes@nearsoft.com").withString("technology", "java"));
            table.putItem(new Item().withPrimaryKey("id_ns", "mrojas", "mail", "mrojas@nearsoft.com").withString("technology", "javascript"));
            table.putItem(new Item().withPrimaryKey("id_ns", "slopez", "mail", "slopez@nearsoft.com").withNumber("desk", 6));

        } catch (Exception e) {
            System.err.println("Unable to add record: ");
            System.err.println(e.getMessage());
        }


        GetItemSpec spec = new GetItemSpec().withPrimaryKey("id_ns", "afentanes", "mail", "afentanes@nearsoft.com");
        GetItemSpec spec2 = new GetItemSpec().withPrimaryKey("id_ns", "slopez", "mail", "slopez@nearsoft.com");


        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }


}
