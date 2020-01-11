package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public Resume get(String uui) {
        return null;
    }

    @Override
    public void delete(String uui) {

    }

    @Override
    int getIndex(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }
}
