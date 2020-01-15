package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    void doSave(Resume resume, Integer index) {
        saveToArrayProcessing(resume, index);
        size++;
    }

    abstract void saveToArrayProcessing(Resume resume, int index);


    @Override
    protected void doUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }


    @Override
    void doDelete(Integer index) {
        deleteInArrayProcessing(index);
        size--;
        storage[size] = null;
    }

    abstract void deleteInArrayProcessing(int index);

    @Override
    boolean exists(Integer index) {
        return index >= 0;
    }

    @Override
    Resume doGet(Integer index) {
        return storage[index];
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
