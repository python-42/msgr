const connectedEvent = new Event('connected');
const selfOutput = document.querySelector("#self-output-template");
const remoteOutput = document.querySelector("#remote-output-template");
const serverOutput = document.querySelector("#server-output-template");
const spaceSpan = "<span class='space-thin'>&nbsp;</span>";

var listenerURL = "/socket/chat";
var senderURL = "/app/chat";
var senderName = "";
var nameAssigned = false;


document.addEventListener("DOMContentLoaded", function (event) {

    document.getElementById("msg-form").addEventListener("submit", function (e) {
        e.preventDefault();
        send();
    });

    connect();
    document.addEventListener("connected", function (event) {
        stompClient.send(senderURL, {}, JSON.stringify({'sender': 'CLIENT_SCRIPT', 'message' : 'REQUEST_USERNAME'}))
    });
});

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        document.dispatchEvent(connectedEvent);
        stompClient.subscribe(listenerURL, (msg) => {
            outputMessage(msg.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function send() {
    var msg = document.getElementById("msg-box").value;
    stompClient.send(senderURL, {}, JSON.stringify({'sender': senderName, 'message': msg}));
    document.getElementById("msg-box").value = "";
}

function outputMessage(data) {
    let sender = JSON.parse(data).sender;
    let msg = JSON.parse(data).message;
    let template;

    if(sender == "SERVER"){
        if(!nameAssigned){
            senderName = msg;
            nameAssigned = true;
        }else{
            template = serverOutput;
            msg = "New user " + msg + " connected."
        }
    }else if(sender == senderName) {
        template = selfOutput;
    }else if(sender != "CLIENT_SCRIPT"){
        template = remoteOutput;
    }

    if(template != null){
        clone = document.importNode(template.content, true);
        if(sender == senderName){
            sender = sender + " (You)";
        }
        clone.querySelector("p").innerHTML = spaceSpan + sender + ": " + msg + spaceSpan;
    
        document.getElementById("message-box").appendChild(clone);
    }

}
