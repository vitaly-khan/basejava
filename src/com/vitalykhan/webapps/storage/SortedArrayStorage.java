package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToArrayProcessing(Resume r, int index) {
        final int localIndex = -index - 1;
        System.arraycopy(storage, localIndex, storage, localIndex + 1, size - localIndex);
        storage[localIndex] = r;
    }

    @Override
    void deleteInArrayProcessing(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }



    @Override
    int getIndex(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }
}
