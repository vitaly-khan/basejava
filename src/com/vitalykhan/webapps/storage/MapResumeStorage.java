package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    void checkNoStorageOverflow(Resume resume) {
        /*No need to check size in MapStorage*/
    }

    @Override
    boolean exists(Resume index) {
        return index != null;
    }

    @Override
    Resume getIndex(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    void doSave(Resume resume, Resume index) {
        resumeMap.put(resume.getUuid(), resume); //index = resume, in this case!
    }

    @Override
    void doUpdate(Resume index, Resume resume) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    void doDelete(Resume index) {
        resumeMap.remove(index.getUuid());
    }

    @Override
    Resume doGet(Resume index) {
        return index;
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
