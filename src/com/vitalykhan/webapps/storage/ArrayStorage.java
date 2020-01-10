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
        if (uuidIsNull(r.getUuid())) {
            return;
        }

        int index = 0;
        if (resumeExists(r) != -1) {
            storage[index] = r;
        } else {
            System.out.println("Resume doesn't exists in DB!");
        }
    }

    /**
     * Searches for given Resume in storage.
     *
     * @param resume given resume
     * @return index of found resume or -1 if not found
     */
    private int resumeExists(Resume resume) {
        int result = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return result;
    }

    public void save(Resume r) {
        if (uuidIsNull(r.getUuid())) {
            return;
        }

        if (size == storage.length) {
            System.out.println("The storage is full! Saving is impossible.");
            return;
        }

        if (resumeExists(r) == -1) {
            storage[size++] = r;
        } else {
            System.out.println("Resume exists in DB already!");
        }
    }

    public Resume get(String uuid) {
        int index;
        Resume resume = new Resume();

        resume.setUuid(uuid);

        if ((index = resumeExists(resume)) == -1) {
            System.out.println("Resume doesn't exists in DB!");
            return null;
        }
        else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index;
        Resume resume = new Resume();


        if (uuidIsNull(uuid)) {
            return;
        }
        resume.setUuid(uuid);
        if ((index = resumeExists(resume)) == -1) {
            System.out.println("Resume doesn't exists in DB!");
        } else {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
            storage[size] = null;
        }
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

    private boolean uuidIsNull(String uuid) {
        if (Objects.isNull(uuid)) {
            System.out.println("Wrong uuid!");
            return true;
        }
        return false;
    }
}
