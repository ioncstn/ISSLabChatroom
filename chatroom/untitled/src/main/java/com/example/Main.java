package com.example;

import com.example.controllers.LogInController;
import com.example.repository.RepositoryChatters;
import com.example.repository.RepositoryMessages;
import com.example.repository.RepositoryORMChatters;
import com.example.repository.RepositoryORMMessages;
import com.example.controllers.Service;
import com.example.controllers.ServiceInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        RepositoryChatters repositoryChatters = new RepositoryORMChatters();
        RepositoryMessages repositoryMessages = new RepositoryORMMessages();

        System.out.println(repositoryChatters.findByUsernameAndPassword("test", "test"));

        ServiceInterface service = new Service(repositoryMessages, repositoryChatters);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chatroom/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LogInController controller = fxmlLoader.getController();
        controller.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}