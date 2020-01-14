package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    void checkNoStorageOverflow(Resume resume) {
        /*No need to check size in ListStorage*/
    }

    @Override
    Object checkResumeDoesntExistAndGetIndex(Resume resume) {
        if (resumeMap.containsKey(resume.getUuid())) {
            throw new ResumeExistsInStorageException(resume.getUuid());
        }
        return resume.getUuid();
    }

    @Override
    Object checkResumeExistsAndGetIndex(Resume resume) {
        if (!resumeMap.containsKey(resume.getUuid())) {
            throw new ResumeDoesntExistInStorageException(resume.getUuid());
        }
        return resume.getUuid();
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
    public List<Resume> getAllSorted() {
        List<Resume> result = new ArrayList<>(resumeMap.values());
        result.sort(RESUME_COMPARATOR);
        return result;
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
