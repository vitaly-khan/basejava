package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        checkSizeWhenSaving(resume);
        Object index = checkResumeDoesntExistAndGetIndex(resume);
        saveProcessing(resume, index);
    }

    //Template pattern
    abstract void checkSizeWhenSaving(Resume resume);
    abstract Object checkResumeDoesntExistAndGetIndex(Resume resume);
    abstract void saveProcessing(Resume resume, Object index);


    @Override
    public final void update(Resume r) {
        final Object index = checkResumeExistsAndGetIndex(r);
        updateProcessing(index, r);
    }

    //Template pattern
    abstract Object checkResumeExistsAndGetIndex(Resume r);
    abstract void updateProcessing(Object index, Resume resume);


    @Override
    public final void delete(String uuid) {
        Resume resume = new Resume(uuid);
        final Object index = checkResumeExistsAndGetIndex(resume);
        deleteProcessing(index);
    }

    //Template pattern
    abstract void deleteProcessing(Object index);


    @Override
    public final Resume get(String uuid) {
        Resume resume = new Resume(uuid);
        final Object index = checkResumeExistsAndGetIndex(resume);
        return getResume(index);
    }

    //Template pattern
    abstract Resume getResume(Object index);




}
