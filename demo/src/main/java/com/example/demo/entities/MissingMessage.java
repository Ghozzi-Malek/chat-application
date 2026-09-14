package com.example.demo.entities;

import org.springframework.data.repository.NoRepositoryBean;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;



@DynamoDbBean 
@Getter 
@Setter 
@NoArgsConstructor  

public class MissingMessage {
    private String chatId;
    private String userId;

    @DynamoDbPartitionKey 
    public String getChatId(){
        return chatId;
    }
    @DynamoDbSortKey 
    public String getUserId(){
        return userId;
    }
}
