package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    public static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public void save(Resume resume) {
        checkNoStorageOverflow(resume);
        Object index = checkResumeDoesntExistAndGetIndex(resume);
        doSave(resume, index);
    }

    //Template pattern
    abstract void checkNoStorageOverflow(Resume resume);
    abstract Object checkResumeDoesntExistAndGetIndex(Resume resume);
    abstract void doSave(Resume resume, Object index);


    @Override
    public final void update(Resume r) {
        final Object index = checkResumeExistsAndGetIndex(r);
        doUpdate(index, r);
    }

    //Template pattern
    abstract Object checkResumeExistsAndGetIndex(Resume r);
    abstract void doUpdate(Object index, Resume resume);


    @Override
    public final void delete(String uuid) {
        Resume resume = new Resume(uuid);
        final Object index = checkResumeExistsAndGetIndex(resume);
        doDelete(index);
    }

    //Template pattern
    abstract void doDelete(Object index);


    @Override
    public final Resume get(String uuid) {
        Resume resume = new Resume(uuid, "");
        final Object index = checkResumeExistsAndGetIndex(resume);
        return doGet(index);
    }

    //Template pattern
    abstract Resume doGet(Object index);




}
