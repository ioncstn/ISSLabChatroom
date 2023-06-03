package com.example.controllers;

import com.example.HelloApplication;
import com.example.domain.Chatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class LogInController {
    private ServiceInterface service;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void setService(ServiceInterface service){
        this.service = service;
    }

    public void checkLogIn(ActionEvent event){
        String user = username.getText();
        String pw = password.getText();
        if(!Objects.equals(pw, "") && !Objects.equals(user, "")){
            Optional<Chatter> chatter = service.logIn(user, pw);
            chatter.ifPresent(value -> switchToLoggedIn(event, value));
        }
    }

    public void switchToLoggedIn(ActionEvent event, Chatter chatter){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chatroom/chatroom-view.fxml"));
            Parent root = fxmlLoader.load();

            LoggedInController controller = fxmlLoader.getController();
            controller.setService(this.service);
            controller.setCurrentUser(chatter);
            service.addObserver(controller);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("logged in");
            stage.show();

        }
        catch(IOException e){
            System.exit(3);
        }
    }

    public void switchToRegisterWindow(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chatroom/register-view.fxml"));
            Parent root = fxmlLoader.load();

            RegisterController controller = fxmlLoader.getController();
            controller.setService(this.service);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("register");
            stage.show();

        }
        catch(IOException e){
            System.exit(3);
        }
    }
}
