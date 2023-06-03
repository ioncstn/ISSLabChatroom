package com.example.controllers;

import com.example.domain.Chatter;
import com.example.domain.Message;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface {
    Optional<Chatter> logIn(String username, String password);
    boolean addChatter(String username, String password);
    void addMessage(String text, Chatter user);

    void ban(int modID, String target);
    void mute(int modID, String target);
    void unmute(int modID, String target);
    List<Message> getAllMessages(String username);
    void addObserver(LoggedInController obs);
}
