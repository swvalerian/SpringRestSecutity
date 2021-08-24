package com.swvalerian.springrestapi.service;

import java.util.List;

public interface BaseService<T, ID> {
    T getId(ID id);
    List<T> getAll();
    T save(T object);
    List<T> update(T object);
    void deleteId(ID id);
}
