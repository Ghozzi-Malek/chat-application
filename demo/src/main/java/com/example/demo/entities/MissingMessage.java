package com.example.demo.entities;

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
    private String userId;
    private String messageId;

    @DynamoDbPartitionKey 
    public String getUserId(){
        return userId;
    }

    @DynamoDbSortKey
    public String getMessageId(){
        return messageId;
    }
}
