package com.example.controllers;

import com.example.HelloApplication;
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

public class RegisterController {
    private ServiceInterface service;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void setService(ServiceInterface service){
        this.service = service;
    }

    public void register(ActionEvent event){

        String user = username.getText();
        String pw = password.getText();
        if(!Objects.equals(pw, "") && !Objects.equals(user, "")){
            boolean result = service.addChatter(user, pw);
            if(result){
                switchToLogIn(event);
            }
        }
    }

    public void switchToLogIn(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chatroom/login-view.fxml"));
            Parent root = fxmlLoader.load();

            LogInController controller = fxmlLoader.getController();
            controller.setService(this.service);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("log IN");
            stage.show();

        }
        catch(IOException e){
            System.exit(3);
        }

    }
}
