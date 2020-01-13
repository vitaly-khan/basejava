package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;

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
    Object checkResumeDoesntExistAndGetIndex(Resume resume) {
        int index = getIndex(resume);
        if (index >= 0) {
            throw new ResumeExistsInStorageException(resume.getUuid());
        }
        return index;
    }

    @Override
    void doSave(Resume resume, Object index) {
        saveToArrayProcessing(resume, (Integer) index);
        size++;
    }

    abstract void saveToArrayProcessing(Resume resume, int index);


    /*UPDATING*/
    @Override
    Object checkResumeExistsAndGetIndex(Resume resume) {
            final int index = getIndex(resume);
            if (index < 0) {
                throw new ResumeDoesntExistInStorageException(resume.getUuid());
            }
            return index;
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage[(int) index] = resume;
    }


    /*DELETING*/
    @Override
    void doDelete(Object index) {
        deleteInArrayProcessing((Integer) index);
        size--;
        storage[size] = null;
    }

    abstract void deleteInArrayProcessing(int index);


    /*GETTING*/
    @Override
    Resume doGet(Object index) {
        return storage[(Integer) index];
    }


    /*OTHERS*/
    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }


    /**
     * Searches for given Resume in storage.
     *
     * @param resume given resume
     * @return index of found resume or -1 if not found
     */
    abstract int getIndex(Resume resume);

}
