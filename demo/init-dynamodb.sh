#!/bin/bash

ENDPOINT="http://localhost:8000"

echo "Creating tables..."

# =========================
# Chat table
# =========================

# docker run -d -p 8000:8000 amazon/dynamodb-local


aws dynamodb create-table \
                --table-name User \
                --attribute-definitions \
                AttributeName=userId,AttributeType=S \
                --key-schema \
                AttributeName=userId,KeyType=HASH \
                --billing-mode PAY_PER_REQUEST \
                --endpoint-url http://localhost:8000 1> /dev/null

aws dynamodb create-table \
                --table-name Members \
                --attribute-definitions \
                AttributeName=chatId,AttributeType=S \
                AttributeName=userId,AttributeType=S \
                --key-schema \
                AttributeName=chatId,KeyType=HASH \
                AttributeName=userId,KeyType=SORT \
                --billing-mode PAY_PER_REQUEST \
                --endpoint-url http://localhost:8000 1> /dev/null

aws dynamodb create-table \
                                --table-name Chat \
                                --attribute-definitions \
                                AttributeName=chatId,AttributeType=S \
                                --key-schema \
                                AttributeName=chatId,KeyType=HASH \
                                --billing-mode PAY_PER_REQUEST \
                                --endpoint-url http://localhost:8000 1> /dev/null

aws dynamodb create-table \
                --table-name Message \
                --attribute-definitions \
                AttributeName=chatId,AttributeType=S \
                AttributeName=timeStamp,AttributeType=N \
                --key-schema \
                AttributeName=chatId,KeyType=HASH\
                AttributeName=timeStamp,KeyType=RANGE \
                --billing-mode PAY_PER_REQUEST \
                --endpoint-url http://localhost:8000 1> /dev/null

# =========================
# Add records
# =========================

echo "Adding records..."


aws dynamodb put-item \
                --table-name Chat \
                --item '{"chatId" : {"S":"222"},"name" : {"S":"Family"}}' \
                --endpoint-url http://localhost:8000

aws dynamodb put-item \
                --table-name Chat \
                --item '{"chatId" : {"S":"123"},"name" : {"S":"Friends"}}' \
                --endpoint-url http://localhost:8000

 
    --endpoint-url "$ENDPOINT"


echo "Database setup complete!"