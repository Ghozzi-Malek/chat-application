# chat_application

This chat application will be a discord like app

## Functional Requirements
- Users should be able to create accounts
- Users should be able to join chats
- Users should be able to send messages

## Non-functional Requirements

- High availabilty
- Reliabilty
- Messages should be in order
- Low latency (fast writes and reads)
- scaleability (stores a lot of messages)

## Core entities

- User
- chat_room
- message

## Api end-points

// get latest 100 message
GET /chat/{id}

Inputs:
    chat id
    auth token

Ouput:

{
    "message1": "lorem",
    "message1 sender": "el bo3",
    .
    .
    .
    "message100": "lorem"
    "message100 sender": "7amouda",
}

// join chat
POST /chat/{id}/join

Inputs:
    chat id
    auth token

OUTPUT:
    http response

// send message
POST /chat/{id}

Inputs:
    chat id
    user id
    message body


OUTPUT:
    http response

// create account
POST /sign-in

OUTPUT:
    http response

// sign up
POST /sign-up

OUTPUT:
    http response


## High level desing