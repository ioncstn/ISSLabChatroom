package com.example.repository;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<T>{
    boolean add(T item);
    Optional<T> find(int ID);
    boolean update(int ID, T item);
    boolean delete(T item);
    List<T> getAll();
}
