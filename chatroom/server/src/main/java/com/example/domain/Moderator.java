package com.example.domain;

public class Moderator extends Chatter {
    private int ID;

    public Moderator(){}
    public Moderator(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
