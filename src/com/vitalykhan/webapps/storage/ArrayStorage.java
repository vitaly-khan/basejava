package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

/**
 * Array based resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveProcessing(Resume r) {
            storage[size] = r;
    }

    @Override
    void deleteProcessing(int index) {
        storage[index] = storage[size - 1];
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
