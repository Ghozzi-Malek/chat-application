package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.demo.entities.Chat;
import com.example.demo.entities.ChatMessage;
import com.example.demo.entities.Members;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
@Repository
@RequiredArgsConstructor

public class ChatsRepositoryImp {
    private final DynamoDbTable<Chat> chatTable;
    private final DynamoDbTable<Members> membersTable;
    private final DynamoDbTable<ChatMessage> messagTable;
    

    public List<Chat> getChats(String userId) {
        Expression userFilter = Expression.builder()
                .expression("userId = :userId")
                .putExpressionValue(":userId", software.amazon.awssdk.services.dynamodb.model.AttributeValue.builder()
                        .s(userId)
                        .build())
                .build();

        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .filterExpression(userFilter)
                .build();

        return membersTable.scan(request)
                .items()
                .stream()
                .map(member -> chatTable.getItem(Key.builder()
                        .partitionValue(member.getChatId())
                        .build()))
                .filter(chat -> chat != null)
                .toList();
    }

    public void saveMessageToDb(ChatMessage message){
        message.setTimeStamp(System.currentTimeMillis());
        messagTable.putItem(message);
        System.out.println(message.getText());
    }

}

