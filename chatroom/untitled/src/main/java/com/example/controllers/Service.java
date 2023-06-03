package com.example.controllers;

import com.example.domain.Chatter;
import com.example.domain.Message;
import com.example.repository.RepositoryChatters;
import com.example.repository.RepositoryMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Service implements ServiceInterface {
    private RepositoryMessages repositoryMessages;
    private RepositoryChatters repositoryChatters;
    private List<LoggedInController> observers = new ArrayList<>();
    public Service(RepositoryMessages repositoryMessages, RepositoryChatters repositoryChatters) {
        this.repositoryMessages = repositoryMessages;
        this.repositoryChatters = repositoryChatters;
    }
    public void addObserver(LoggedInController obs){
        observers.add(obs);
    }
    private void notifyAllClients(){
        for(var obs: observers){
            obs.update();
        }
    }

    private void notifyCertainClient(String receiver){
        for(var obs: observers){
            if (Objects.equals(obs.getCurrentUser().getUsername(), receiver)){
                obs.update();
                break;
            }
        }
    }

    private void notifyBan(String receiver){
        for(var obs: observers){
            if (Objects.equals(obs.getCurrentUser().getUsername(), receiver)){
                obs.reactBan();
                break;
            }
        }
    }
    private void notifyMute(String receiver){
        for(var obs: observers){
            if (Objects.equals(obs.getCurrentUser().getUsername(), receiver)){
                obs.reactMute();
                break;
            }
        }
    }
    private void notifyUnmute(String receiver){
        for(var obs: observers){
            if (Objects.equals(obs.getCurrentUser().getUsername(), receiver)){
                obs.reactUnmute();
                break;
            }
        }
    }
    @Override
    public Optional<Chatter> logIn(String username, String password) {
        return repositoryChatters.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean addChatter(String username, String password) {
        return repositoryChatters.add(new Chatter(username, password, false));
    }

    @Override
    public void addMessage(String text, Chatter user) {
        if(!repositoryChatters.isMuted(user.getID())) {
            String username = user.getUsername();
            if (text.startsWith("/")) {
                var items = text.split(" ");
                switch (items[0]) {
                    case "/w": {
                        if (items.length >= 3) {
                            String receiver = items[1];
                            if (repositoryChatters.findByUsername(receiver).isPresent()) {
                                repositoryMessages.add(new Message(text.substring(4 + receiver.length()), username, receiver));
                                this.notifyCertainClient(username);
                                this.notifyCertainClient(receiver);
                            }
                        }
                        break;
                    }
                    case "/ban": {
                        if (items.length == 2) {
                            this.ban(user.getID(), items[1]);
                        }
                        break;
                    }
                    case "/mute": {
                        if (items.length == 2) {
                            this.mute(user.getID(), items[1]);
                        }
                        break;
                    }
                    case "/unmute": {
                        if (items.length == 2) {
                            this.unmute(user.getID(), items[1]);
                        }
                        break;
                    }
                }
            } else {
                repositoryMessages.add(new Message(text, user.getUsername()));
                this.notifyAllClients();
            }
        }
    }

    @Override
    public void ban(int modID, String target) {
        var chat = repositoryChatters.findByUsername(target);
        if(chat.isPresent()) {
            if (repositoryChatters.isModerator(modID)) {
                if (!repositoryChatters.isModerator(chat.get().getID())){
                    repositoryChatters.delete(chat.get());
                    notifyBan(target);
                }
            }
        }
    }

    @Override
    public void mute(int modID, String target) {
        var chat = repositoryChatters.findByUsername(target);
        if(chat.isPresent()) {
            if (repositoryChatters.isModerator(modID)) {
                if (!repositoryChatters.isModerator(chat.get().getID())){
                    var chatter = chat.get();
                    chatter.setMuted(true);
                    repositoryChatters.update(0, chatter);
                    this.notifyMute(target);
                }
            }
        }
    }

    @Override
    public void unmute(int modID, String target) {
        var chat = repositoryChatters.findByUsername(target);
        if(chat.isPresent()) {
            if (repositoryChatters.isModerator(modID)) {
                if (!repositoryChatters.isModerator(chat.get().getID())){
                    var chatter = chat.get();
                    chatter.setMuted(false);
                    repositoryChatters.update(0, chatter);
                    this.notifyUnmute(target);
                }
            }
        }
    }

    public List<Message> getAllMessages(String username){
        var res = repositoryMessages.getAllForUser(username);
        for(var msg: res){
            if(msg.getReceiver() != null) {
                msg.setText("WHISPER: " + msg.getText());
            }
        }
        return res;
    }
}
