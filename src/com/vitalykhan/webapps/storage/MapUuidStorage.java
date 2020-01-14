package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    void checkNoStorageOverflow(Resume resume) {
        /*No need to check size in MapStorage*/
    }

    @Override
    boolean exists(Object index) {
        return resumeMap.containsKey(index);
    }

    @Override
    Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    void doSave(Resume resume, Object index) {
        resumeMap.put((String) index, resume);
    }

    @Override
    void doUpdate(Object index, Resume resume) {
        resumeMap.put((String) index, resume);
    }

    @Override
    void doDelete(Object index) {
        resumeMap.remove(index);
    }

    @Override
    Resume doGet(Object index) {
        return resumeMap.get(index);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    List<Resume> doGetAll() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
