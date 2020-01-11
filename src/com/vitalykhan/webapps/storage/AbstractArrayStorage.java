package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractArrayStorage implements Storage {
    static final int STORAGE_LIMIT = 10000;
    Resume[] storage = new Resume[STORAGE_LIMIT];
    int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }


    <T> boolean entityIsNull(T obj) {
        if (Objects.isNull(obj)) {
            System.out.println("Wrong parameter!");
            return true;
        }
        return false;
    }

    /**
     * Searches for given Resume in storage.
     *
     * @param resume given resume
     * @return index of found resume or -1 if not found
     */
    abstract int getIndex(Resume resume);



}
