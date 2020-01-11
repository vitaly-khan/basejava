package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

/**
 * Array based resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
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

    @Override
    public void save(Resume r) {
        if (entityIsNull(r)) {
            return;
        }

        if (size == STORAGE_LIMIT) {
            System.out.println("The storage is full! Saving is impossible.");
            return;
        }

        if (getIndex(r) == -1) {
            storage[size++] = r;
        } else {
            System.out.printf("Resume with id=%s already exists in DB!", r.getUuid());
        }
    }

    @Override
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

    @Override
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
