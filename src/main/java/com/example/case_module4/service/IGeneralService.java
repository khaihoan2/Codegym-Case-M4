package com.example.case_module4.service;

import com.example.case_module4.model.Review;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    T save(T t);

    void deleteById(Long id);
}
