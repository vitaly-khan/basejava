package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

/**
 * Array based resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToArrayProcessing(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    void deleteInArrayProcessing(int index) {
        storage[index] = storage[size - 1];
    }


    Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

}
