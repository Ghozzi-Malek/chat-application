package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.demo.entities.*;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Repository
@RequiredArgsConstructor

public class ChatsRepositoryImp {
    

    private DynamoDbTable<Members> members;

    public List<String> getChats(String userId) {
        DynamoDbIndex<Members> index = members.index("UserChatsIndex");
        QueryConditional condition =
         QueryConditional.keyEqualTo(
            Key.builder()
               .partitionValue(userId)
               .build()
        );

        return index.query(condition)
                .stream()
                .flatMap(page -> page.items().stream())
                .map(Members::getChatId)
                .toList();
    }
}
