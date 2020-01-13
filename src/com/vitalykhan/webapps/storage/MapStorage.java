package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    void checkSizeWhenSaving(Resume resume) {
        /*No need to check size in ListStorage*/
    }

    @Override
    Object checkResumeDoesntExistAndGetIndex(Resume resume) {
        return null;
    }

    @Override
    void saveProcessing(Resume resume, Object index) {

    }

    @Override
    void updateProcessing(Object index, Resume resume) {

    }

    @Override
    void deleteProcessing(Object index) {

    }

    @Override
    Resume getResume(Object index) {
        return null;
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }


    @Override
    Object checkResumeExistsAndGetIndex(Resume r) {
        return null;
    }



    @Override
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
