package com.example.ewaserver.repositories;

import java.util.List;

public interface EntityRepository<E> {

    List<E> findAll();

    E findById(int id);

    E Save(E entity);

    E update(E entity, int id);

    E deleteById(int id);

    List<E> findByQuery(String jpqlName, Object... params);

}
