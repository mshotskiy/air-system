package com.shotskiy.airsystem.service;

import java.util.List;

public interface AbstractEntityService<T> {
    List<T> getAll();

    T get(Long id);

    T save(T obj);

    void deleteById(Long id);

    T update(T obj, Long id);
}
