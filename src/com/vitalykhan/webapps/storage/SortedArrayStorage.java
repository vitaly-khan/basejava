package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveProcessing(Resume r) {
        final int index = -getIndex(r) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = r;
    }

    @Override
    void deleteProcessing(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }



    @Override
    int getIndex(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }
}
