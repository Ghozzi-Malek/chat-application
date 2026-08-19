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

public class ChatMessage {
    
    private String chatId;
    private String messagId;
    private String senderId;
    private String text;
    private long timeStamp;

    @DynamoDbPartitionKey
    public String getChatId() {
        return chatId;
    }

    @DynamoDbSortKey
    public long getTimeStamp(){
        return timeStamp;
    }

    


}
