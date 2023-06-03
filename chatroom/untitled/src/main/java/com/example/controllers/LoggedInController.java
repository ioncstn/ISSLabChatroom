package com.example.controllers;

import com.example.domain.Chatter;
import com.example.domain.Message;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.swing.*;

public class LoggedInController {
    private ObservableList<Message> modelMessages = FXCollections.observableArrayList();
    private ServiceInterface service;
    private Chatter currentUser;
    @FXML
    private ListView<Message> messages_view;
    @FXML
    private TextArea text_field;

    @FXML
    private Label hello_label;

    public void setService(ServiceInterface service){
        this.service = service;
        this.init();
    }
    public void setCurrentUser(Chatter user){
        currentUser = user;
        hello_label.setText(hello_label.getText() + currentUser.getUsername());
    }

    public Chatter getCurrentUser(){
        return currentUser;
    }
    private void init(){
        messages_view.setItems(modelMessages);
        messages_view.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new CustomListViewCellMessage();
            }
        });
        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
        pause.setOnFinished(o -> this.update());
        pause.play();
    }
    public void sendMessage(ActionEvent event){
        if(text_field.getText().length() > 0){
            service.addMessage(text_field.getText(), currentUser);
            text_field.clear();
        }
    }
    public void update(){
        var res = service.getAllMessages(currentUser.getUsername());
        res.add(new Message("", ""));
        modelMessages.setAll(res);
        messages_view.scrollTo(modelMessages.size() - 1);
        messages_view.scrollTo(modelMessages.get(modelMessages.size() - 1));
    }

    public void reactBan(){
        Stage stage = (Stage) text_field.getScene().getWindow();
        stage.close();
        JOptionPane.showMessageDialog(null, "you've been banned, bOZO", "ban :( on account: " + currentUser.getUsername(), JOptionPane.INFORMATION_MESSAGE);
    }
    public void reactMute(){
        JOptionPane.showMessageDialog(null, "you've been muted...", "MUTE WARNING " + currentUser.getUsername(), JOptionPane.INFORMATION_MESSAGE);
    }
    public void reactUnmute(){
        JOptionPane.showMessageDialog(null, "you've been unmuted...", "UNMUTE WARNING " + currentUser.getUsername(), JOptionPane.INFORMATION_MESSAGE);
    }
}
