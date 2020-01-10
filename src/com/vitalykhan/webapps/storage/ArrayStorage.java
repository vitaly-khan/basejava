package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        if (entityIsNull(r)) {
            return;
        }

        int index = 0;
        if (getIndex(r) != -1) {
            storage[index] = r;
        } else {
            System.out.println("Resume doesn't exist in DB!");
        }
    }

    /**
     * Searches for given Resume in storage.
     *
     * @param resume given resume
     * @return index of found resume or -1 if not found
     */
    private int getIndex(Resume resume) {
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return result;
    }

    public void save(Resume r) {
        if (entityIsNull(r)) {
            return;
        }

        if (size == storage.length) {
            System.out.println("The storage is full! Saving is impossible.");
            return;
        }

        if (getIndex(r) == -1) {
            storage[size++] = r;
        } else {
            System.out.printf("Resume with id=%s already exists in DB!", r.getUuid());
        }
    }

    public Resume get(String uuid) {
        if (entityIsNull(uuid)) {
            return null;
        }


        Resume resume = new Resume();
        resume.setUuid(uuid);

        int index = getIndex(resume);
        if (index == -1) {
            System.out.printf("Resume %s doesn't exist in DB!", resume);
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        if (entityIsNull(uuid)) {
            return;
        }

        Resume resume = new Resume();
        resume.setUuid(uuid);


        int index = getIndex(resume);
        if (index == -1) {
            System.out.printf("Resume %s doesn't exist in DB!", resume);
            return;
        }

        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);

//        return Arrays.stream(storage)
//                .limit(size)
//                .toArray(Resume[]::new);
    }

    public int size() {
        return size;
    }

    private <T> boolean entityIsNull(T obj) {
        if (Objects.isNull(obj)) {
            System.out.println("Wrong parameter!");
            return true;
        }
        return false;
    }
}
