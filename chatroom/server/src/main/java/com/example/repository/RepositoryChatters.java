package com.example.repository;

import com.example.domain.Chatter;

import java.util.List;
import java.util.Optional;

public interface RepositoryChatters extends RepositoryInterface<Chatter>{
    Optional<Chatter> findByUsernameAndPassword(String username, String password);
    boolean isModerator(int ID);
    boolean isMuted(int ID);
    Optional<Chatter> findByUsername(String username);
}
