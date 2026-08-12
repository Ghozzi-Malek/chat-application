package com.example.demo.entities;

import org.springframework.data.repository.NoRepositoryBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Getter
@Setter
@NoArgsConstructor

public class User {
    public String userId;
    public String name;
    public String email;
    public String psswd;

    @DynamoDbPartitionKey
    public String getUserId(){
        return userId;
    }
}
