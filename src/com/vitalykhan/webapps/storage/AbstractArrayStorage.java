package com.vitalykhan.webapps.storage;

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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);

        int index = getIndex(resume);
        if (index == -1) {
            System.out.printf("Resume %s doesn't exist in DB!", resume);
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        Resume resume = new Resume();
        resume.setUuid(uuid);

        int index = getIndex(resume);
        if (index == -1) {
            System.out.printf("Resume %s doesn't exist in DB!%n", resume);
            return;
        }

        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }s
        size--;
        storage[size] = null;
    }

    /**
     * Searches for given Resume in storage.
     *
     * @param resume given resume
     * @return index of found resume or -1 if not found
     */
    abstract int getIndex(Resume resume);



}
