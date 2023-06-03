package com.example;

import com.example.domain.Chatter;
import com.example.domain.Message;
import com.example.repository.RepositoryChatters;
import com.example.repository.RepositoryMessages;
import com.example.repository.RepositoryORMChatters;
import com.example.repository.RepositoryORMMessages;

import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        RepositoryMessages repositoryMessages = new RepositoryORMMessages();
        RepositoryChatters repositoryChatters = new RepositoryORMChatters();
        System.out.println("< < < < < < < < < starting the tests... > > > > > > > > > >");
        repositoryMessages.add(new Message("acesta e un mesaj de test", "costinel"));
        if(repositoryMessages.getAll().size() > 0){
            System.out.println("acestea sunt mesajele prezente in baza de date: ");
            for(Message message: repositoryMessages.getAll()){
                System.out.println(message.getID() + ". " + message.getUsername() + ": " + message.getText());
            }
        }
        repositoryChatters.add(new Chatter("test", "test", false));
        if(repositoryChatters.findByUsernameAndPassword("test", "test").isPresent()){
            System.out.println("a fost gasit user-ul cu username si password test");
        }
        System.out.println("< < < < < < < < < ended the tests... > > > > > > > > > >");
    }
}