package com.example.domain;

import java.util.List;

public class Chatter {
    private int ID;
    private String username;
    private String password;
    private Boolean muted;

    private List<Chatter> friends;
    private List<Chatter> friendsOf;

    public Chatter(){}

    public Chatter(String username, String password, Boolean isMuted) {
        this.username = username;
        this.password = password;
        this.muted = isMuted;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getMuted() {
        return muted;
    }

    public void setMuted(Boolean muted) {
        this.muted = muted;
    }

    public List<Chatter> getFriends() {
        return friends;
    }

    public void setFriends(List<Chatter> friends) {
        this.friends = friends;
    }

    public List<Chatter> getFriendsOf() {
        return friendsOf;
    }

    public void setFriendsOf(List<Chatter> friendsOf) {
        this.friendsOf = friendsOf;
    }
}
