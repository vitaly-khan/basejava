package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

/**
 * Array based resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void update(Resume r) {
        int index = 0;
        if (getIndex(r) != -1) {
            storage[index] = r;
        } else {
            System.out.println("Resume doesn't exist in DB!");
        }
    }

    @Override
    public void save(Resume r) {
        if (size == STORAGE_LIMIT) {
            System.out.println("The storage is full! Saving is impossible.");
            return;
        }

        if (getIndex(r) == -1) {
            storage[size++] = r;
        } else {
            System.out.printf("Resume with id=%s already exists in DB!%n", r.getUuid());
        }
    }


    int getIndex(Resume resume) {
        int result = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resume)) {
                return i;
            }
        }
        return result;
    }

}
