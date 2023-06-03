package com.example.repository;

import com.example.domain.Message;

import java.util.List;

public interface RepositoryMessages extends RepositoryInterface<Message>{
    List<Message> getAllForUser(String username);
}
