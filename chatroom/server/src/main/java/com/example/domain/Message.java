package com.example.domain;

public class Message {
    private int ID;
    private String text;
    private String username;
    private String receiver;

    public Message(){}

    public Message(String text, String username, String receiver){
        this.text = text;
        this.username = username;
        this.receiver = receiver;
    }
    public Message(String text, String username){
        this.text = text;
        this.username = username;
        this.receiver = null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
