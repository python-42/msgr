package com.chat.test.websocket;

public class Message {
    private String sender;
    private String message;

    public Message(){}

    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender(){
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return message + " - From " + sender;
    }
}
