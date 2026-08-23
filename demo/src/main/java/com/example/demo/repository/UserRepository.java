package com.example.demo.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DynamoDbTable<User> userTable;

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

    public void save(User user) {
        userTable.putItem(user);
    }
}
