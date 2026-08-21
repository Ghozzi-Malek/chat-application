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
        stompClient.subscribe('/topic/messages', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
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
function sendMessage() {
    if (!stompClient) return;
    var text = $("#message").val();
    if (!text || text.trim() === '') return;
    stompClient.send("/app/chat.send", {}, JSON.stringify({'text': text,'chatId': getCurrentChatId()}));
    // clear input after sending
    $("#message").val('');
}


function showGreeting(message) {
    // append a message element into the message feed
    $("#greetings").append(
        "<article class='message outgoing'><div class='message-avatar'>Y</div>" +
        "<div class='message-body'><div class='message-meta'><strong>You</strong><span></span></div>" +
        "<p>" + message.text + "</p></div></article>"
    );
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
})