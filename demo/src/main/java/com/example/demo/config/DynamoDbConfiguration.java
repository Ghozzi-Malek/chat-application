package com.example.demo.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entities.Chat;
import com.example.demo.entities.ChatMessage;
import com.example.demo.entities.Members;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfiguration {

    @Bean
    DynamoDbClient dynamoDbClient(
            @Value("${aws.region:us-east-1}") String region,
            @Value("${aws.endpoint-url:}") String endpointUrl) {
        var builder = DynamoDbClient.builder()
                .region(Region.of(region))
                ;

        if (!endpointUrl.isBlank()) {
            builder.endpointOverride(URI.create(endpointUrl));
        }

        return builder.build();
    }

    @Bean
    DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    DynamoDbTable<Chat> chatTable(
            DynamoDbEnhancedClient dynamoDbEnhancedClient,
            @Value("${aws.dynamodb.chat-table:Chat}") String tableName) {
        return dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(Chat.class));
    }
    @Bean
    DynamoDbTable<ChatMessage> MessageTable(
            DynamoDbEnhancedClient dynamoDbEnhancedClient,
            @Value("${aws.dynamodb.message-table}") String tableName) {
        return dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(ChatMessage.class));
        
    }
    @Bean
    DynamoDbTable<Members> membersTable(
        DynamoDbEnhancedClient client,
        @Value("${aws.dynamodb.members-table}") String tableName) {
    return client.table(tableName, TableSchema.fromBean(Members.class));
}
}