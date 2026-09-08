package com.example.demo.repository;

import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.*;
import java.util.List;
import lombok.RequiredArgsConstructor;

import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DynamoDbTable<User> userTable;
    private final DynamoDbTable<Members> membersTable;

    public Optional<User> findByEmail(String email) {
        Expression emailFilter = Expression.builder()
                .expression("email = :email")
                .expressionValues(Map.of(
                        ":email", AttributeValue.builder().s(email).build()))
                .build();

        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .filterExpression(emailFilter)
                .build();

        return userTable.scan(request)
                .items()
                .stream()
                .findFirst();
    }
    
    public String findById(String userId){
        QueryConditional query = QueryConditional.keyEqualTo(
                Key.builder()
                .partitionValue(userId)
                .build()       
        );

        return userTable.query(query)
                        .items()
                        .stream()
                        .toList()
                        .get(0)
                        .getName();
    }

    public void save(User user) {
        userTable.putItem(user);
    }
    public List<Members> ChatsMembers(ChatMessage message){
        QueryConditional query = QueryConditional.keyEqualTo(
            Key.builder()
               .partitionValue(message.getChatId())
               .build()   
        );
        return membersTable.query(
            QueryEnhancedRequest.builder()
                                .queryConditional(query)
                                .build()
        )
        .items()
        .stream()
        .toList();   
    }
}
