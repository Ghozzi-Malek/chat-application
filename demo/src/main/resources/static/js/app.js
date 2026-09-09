var stompClient = null;
let currentChatId = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function changeCurrentChatId(id) {
    console.log(id)
    currentChatId = id;
    $("#greetings").empty();
    getMessages();
}

function getCurrentChatId() {
    return currentChatId;
}




function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    // enable client debug logs in the browser console
    stompClient.debug = console.log;
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // subscribe to the server destination that the backend sends to
        stompClient.subscribe('/user/queue/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
        stompClient.subscribe('/user/queue/chat-history', function (message) {
            JSON.parse(message.body).forEach(showMessage);
        });
        stompClient.subscribe('/user/queue/chat-joined', function (message) {
            addJoinedChat(JSON.parse(message.body));
        });
        stompClient.subscribe('/user/queue/chat-join-error', function (message) {
            alert(message.body);
        });
    });
}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");

}

function getMessages(){
    if (!stompClient || !currentChatId) return;
    stompClient.send("/app/chat.messages",{},JSON.stringify({'chatId':getCurrentChatId()}));
}

function sendMessage() {
    if (!stompClient) return;
    var text = $("#message").val();
    if (!text || text.trim() === '') return;
    stompClient.send("/app/chat.send", {}, JSON.stringify({'text': text,'chatId': getCurrentChatId()}));
    // clear input after sending
    $("#message").val('');
}

function joinChat() {
    const chatName = $("#chat-name").val().trim();
    if (!stompClient || !chatName) return;

    stompClient.send(
        "/app/chat.join",
        {},
        JSON.stringify({ chatName: chatName })
    );
    $("#chat-name").val("");
}

function addJoinedChat(chat) {
    const chatItem = $("<article>", {
        class: "chat-item",
        "data-chat-id": chat.chatId
    }).on("click", function () {
        changeCurrentChatId(chat.chatId);
    });
    $("<div>", {
        class: "chat-avatar",
        text: chat.name.charAt(0).toUpperCase()
    }).appendTo(chatItem);
    const meta = $("<div>", { class: "chat-meta" }).appendTo(chatItem);
    const row = $("<div>", { class: "chat-row" }).appendTo(meta);
    $("<h2>", { text: chat.name }).appendTo(row);
    $(".chat-list").append(chatItem);
    changeCurrentChatId(chat.chatId);
}


function showMessage(message) {
    if (message.chatId !== getCurrentChatId()) return;

    const senderName = message.senderId === currentUserId ? "You" :message.senderName;
    const messageElement = $("<article>", {
        class: "message " + (message.senderId === currentUserId ? "outgoing" : "incoming")
    });
    const avatar = $("<div>", {
        class: "message-avatar",
        text: senderName ? senderName.charAt(0).toUpperCase() : "?",
        
    });    const body = $("<div>", { class: "message-body" });
    const meta = $("<div>", { class: "message-meta" });
    const sender = $("<strong>", { text: senderName });
    const time = $("<span>", { text: formatMessageTime(message.timeStamp) });
    const text = $("<p>", { text: message.text });

    meta.append(sender, time);
    body.append(meta, text);
    messageElement.append(avatar, body);
    $("#greetings").append(messageElement);
    $(".message-feed").scrollTop($(".message-feed")[0].scrollHeight);
}

function formatMessageTime(timeStamp) {
    return new Date(timeStamp).toLocaleTimeString([], {
        hour: "numeric",
        minute: "2-digit"
    });
}

$(function(){
    connect();
})

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    console.log(chats);
    $( "#send" ).click(function() { sendMessage(); });
    $( "#join-chat" ).click(function() { joinChat(); });
})