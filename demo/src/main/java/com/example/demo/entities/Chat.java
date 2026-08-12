package com.example.demo.entities;

import org.springframework.data.repository.NoRepositoryBean;

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
public class Chat {
    private String chatId;
    private String name;

    @DynamoDbPartitionKey
    public String getChatId() {
        return chatId;
    }


}
