# Chat application



## Functional requirements
- Users should be able to start chats
- Users should be able to send messages
- Users should be able to receive messages(whether they are online when messages are sent or offline)

## Non functional requirements
- low latency(~500ms)
- high availabilty
- scalining


## api

In this application we will use websockets to connect users and servers and as a result we wont have regular endpoints.

### Commands

- sendMessage

{
    "type": "sendMessage",
    "chatId": "",
    "message": "",
} -> {
    "SUCCESS" | " FAILURE",
     "message"Id": "",
}

- receiveMessage

{
    "type": "receiveMessage",
    "chatId": "",
    "messageId": "",
    "UserId": "",

}
- createChat

{
    "chatName": "",
    "participants": [],
} -> {
    "chatId": "",
}



## High level design
