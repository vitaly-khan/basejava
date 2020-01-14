package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;
    Resume[] storage = new Resume[STORAGE_LIMIT];

    int size = 0;

    /*SAVING*/
    @Override
    void checkNoStorageOverflow(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    void doSave(Resume resume, Object index) {
        saveToArrayProcessing(resume, (Integer) index);
        size++;
    }

    abstract void saveToArrayProcessing(Resume resume, int index);


    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage[(int) index] = resume;
    }


    @Override
    void doDelete(Object index) {
        deleteInArrayProcessing((Integer) index);
        size--;
        storage[size] = null;
    }

    abstract void deleteInArrayProcessing(int index);

    @Override
    boolean exists(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    List<Resume> doGetAll() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));
    }
}
