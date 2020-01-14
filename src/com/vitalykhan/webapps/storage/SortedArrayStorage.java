package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    public static final Comparator<Resume> RESUME_COMPARATOR_BY_UUID =
            Comparator.comparing(Resume::getUuid);


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
    Integer getIndex(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey,
                RESUME_COMPARATOR_BY_UUID);
    }
}
