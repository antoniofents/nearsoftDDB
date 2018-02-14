package com.nearsoft.ddb.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.nearsoft.ddb.dao.NearsfotnianDynamoDao;
import com.nearsoft.ddb.dao.NearsoftnianDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.nearsoft.ddb")
public class NearsoftConfiguration {


    @Bean("nearsoftniansDao")
    public NearsoftnianDao nearsoftniansDaoImpl(){
        return new NearsfotnianDynamoDao("nearsoftnians");
    }

    @Bean("dynamoClient")
    public AmazonDynamoDB dynamoDb(){
        /*return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://ec2-54-187-146-120.us-west-2.compute.amazonaws.com", "us-west-2"))
                .build();*/
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new ProfileCredentialsProvider("default"))
                .build();
    }
}
