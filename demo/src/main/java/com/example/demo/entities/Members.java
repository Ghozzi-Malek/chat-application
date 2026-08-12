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
public class Members {
    public String chatId;
    public String userId;

    @DynamoDbPartitionKey
    public String getChatId() {
        return chatId;
    }

    @DynamoDbSortKey
    public String getUserId(){
        return userId;
    }
}
