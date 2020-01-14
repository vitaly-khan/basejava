package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    public static final Comparator<Resume> RESUME_COMPARATOR_BY_NAME =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);


    abstract void checkNoStorageOverflow(Resume resume);
    abstract void doSave(Resume resume, Object index);
    abstract void doUpdate(Object index, Resume resume);
    abstract void doDelete(Object index);
    abstract Resume doGet(Object index);
    abstract List<Resume> doGetAll();

    abstract boolean exists(Object index);
    abstract Object getIndex(String uuid);

    @Override
    public void save(Resume resume) {
        checkNoStorageOverflow(resume);
        Object index = checkResumeDoesntExistAndGetIndex(resume.getUuid());
        doSave(resume, index);
    }

    @Override
    public final void update(Resume r) {
        final Object index = checkResumeExistsAndGetIndex(r.getUuid());
        doUpdate(index, r);
    }

    @Override
    public final void delete(String uuid) {
        final Object index = checkResumeExistsAndGetIndex(uuid);
        doDelete(index);
    }

    @Override
    public final Resume get(String uuid) {
        final Object index = checkResumeExistsAndGetIndex(uuid);
        return doGet(index);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = doGetAll();
        resumeList.sort(RESUME_COMPARATOR_BY_NAME);
        return resumeList;
    }


    private Object checkResumeDoesntExistAndGetIndex(String uuid) {
        Object index = getIndex(uuid);
        if (exists(index)) {
            throw new ResumeExistsInStorageException(uuid);
        }
        return index;
    }


    private Object checkResumeExistsAndGetIndex(String uuid) {
        Object index = getIndex(uuid);
        if (!exists(index)) {
            throw new ResumeDoesntExistInStorageException(uuid);
        }
        return index;
    }

}
