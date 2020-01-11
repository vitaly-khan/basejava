package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

public interface Storage {
    void clear();
    void update(Resume r);
    void save(Resume r);
    Resume get(String uui);
    void delete(String uui);
    Resume[] getAll();
    int size();
}
