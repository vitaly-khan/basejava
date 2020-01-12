package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;

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

    @Override
    public final void update(Resume r) {
        final int index = getIndex(r);
        if (index < 0) {
            throw new ResumeDoesntExistInStorageException(r.getUuid());
        }
        storage[index] = r;
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }

        if (getIndex(r) >= 0) {
            throw new ResumeExistsInStorageException(r.getUuid());
        }
        saveProcessing(r);
        size++;
    }

    abstract void saveProcessing(Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public final Resume get(String uuid) {
        Resume resume = new Resume(uuid);

        final int index = getIndex(resume);
        if (index < 0) {
            throw new ResumeDoesntExistInStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public final void delete(String uuid) {
        Resume resume = new Resume(uuid);

        final int index = getIndex(resume);
        if (index < 0) {
            throw new ResumeDoesntExistInStorageException(uuid);
        }

        deleteProcessing(index);
        size--;
        storage[size] = null;
    }

    abstract void deleteProcessing(int index);

    /**
     * Searches for given Resume in storage.
     *
     * @param resume given resume
     * @return index of found resume or -1 if not found
     */
    abstract int getIndex(Resume resume);



}
