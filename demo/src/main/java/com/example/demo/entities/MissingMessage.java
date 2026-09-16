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
    private long timeStamp;

    @DynamoDbPartitionKey 
    public String getUserId(){
        return userId;
    }

    @DynamoDbSortKey
    public long getTimeStamp(){
        return timeStamp;
    }
}
