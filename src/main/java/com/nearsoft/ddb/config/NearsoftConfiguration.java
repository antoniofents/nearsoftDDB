package com.nearsoft.ddb.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.nearsoft.ddb.dao.NearsfotnianDaoDynamo;
import com.nearsoft.ddb.dao.NearsoftnianDao;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.nearsoft.ddb")
public class NearsoftConfiguration {


    @Bean("nearsoftniansDao")
    public NearsoftnianDao nearsoftniansDaoImpl(){
        return new NearsfotnianDaoDynamo("nearsoftnians");
    }

    @Bean("dynamoClient")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonDynamoDB dynamoDb(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        return client;
    }
}
