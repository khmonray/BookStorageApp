package ru.khusnullin.bookstorageapp.repository;

import java.util.List;

public interface CommonRepository<T> {
    List<T> findAll();

    T findById(int id);

    void save(T entity);

    void delete(int id);

    void update(T t);
}
