package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    void checkNoStorageOverflow(Resume resume) {
        /*No need to check size in ListStorage*/
    }

    @Override
    Object checkResumeDoesntExistAndGetIndex(Resume resume) {
        return null;
    }

    @Override
    void doSave(Resume resume, Object index) {

    }

    @Override
    void doUpdate(Object index, Resume resume) {

    }

    @Override
    void doDelete(Object index) {

    }

    @Override
    Resume doGet(Object index) {
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
