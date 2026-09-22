#!/bin/bash

ENDPOINT="${DYNAMODB_ENDPOINT:-http://localhost:8000}"

echo "Creating tables..."

# =========================
# Chat table
# =========================

until aws dynamodb list-tables --endpoint-url "$ENDPOINT" --region us-east-1 >/dev/null 2>&1; do
  echo "Waiting for DynamoDB Local..."
  sleep 2
done

aws dynamodb create-table \
                --table-name User \
                --attribute-definitions \
                AttributeName=userId,AttributeType=S \
                --key-schema \
                AttributeName=userId,KeyType=HASH \
                --billing-mode PAY_PER_REQUEST \
                --endpoint-url "$ENDPOINT" --region us-east-1 1> /dev/null

aws dynamodb create-table \
                --table-name Members \
                --attribute-definitions \
                AttributeName=chatId,AttributeType=S \
                AttributeName=userId,AttributeType=S \
                --key-schema \
                AttributeName=chatId,KeyType=HASH \
                AttributeName=userId,KeyType=SORT \
                --billing-mode PAY_PER_REQUEST \
                --endpoint-url "$ENDPOINT" --region us-east-1 1> /dev/null

aws dynamodb create-table \
                                --table-name Chat \
                                --attribute-definitions \
                                AttributeName=chatId,AttributeType=S \
                                --key-schema \
                                AttributeName=chatId,KeyType=HASH \
                                --billing-mode PAY_PER_REQUEST \
                                --endpoint-url "$ENDPOINT" --region us-east-1 1> /dev/null

aws dynamodb create-table \
                --table-name Message \
                --attribute-definitions \
                AttributeName=chatId,AttributeType=S \
                AttributeName=timeStamp,AttributeType=N \
                --key-schema \
                AttributeName=chatId,KeyType=HASH \
                AttributeName=timeStamp,KeyType=RANGE \
                --billing-mode PAY_PER_REQUEST \
                --endpoint-url "$ENDPOINT" --region us-east-1 1> /dev/null

aws dynamodb create-table \
    --table-name MissingMessage \
    --attribute-definitions \
    AttributeName=userId,AttributeType=S \
    AttributeName=timeStamp,AttributeType=N \
    --key-schema \
                AttributeName=userId,KeyType=HASH \
                AttributeName=timeStamp,KeyType=RANGE \
    --billing-mode PAY_PER_REQUEST \
    --endpoint-url "$ENDPOINT" --region us-east-1 1>/dev/null

# =========================
# Add records
# =========================

echo "Adding records..."


aws dynamodb put-item \
                --table-name Chat \
                --item '{"chatId" : {"S":"222"},"name" : {"S":"Family"}}' \
                --endpoint-url "$ENDPOINT" --region us-east-1

aws dynamodb put-item \
                --table-name Chat \
                --item '{"chatId" : {"S":"123"},"name" : {"S":"Friends"}}' \
                --endpoint-url "$ENDPOINT" --region us-east-1



echo "Database setup complete!"