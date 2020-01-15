package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {
    private static final Logger log = Logger.getLogger("AbstractStorage");

    public static final Comparator<Resume> RESUME_COMPARATOR_BY_NAME =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);


    abstract void checkNoStorageOverflow(Resume resume);
    abstract void doSave(Resume resume, T index);
    abstract void doUpdate(T index, Resume resume);
    abstract void doDelete(T index);
    abstract Resume doGet(T index);
    abstract List<Resume> doGetAll();

    abstract boolean exists(T index);
    abstract T getIndex(String uuid);

    @Override
    public void save(Resume resume) {
        log.info("Save " + resume);
        checkNoStorageOverflow(resume);
        T index = checkResumeDoesntExistAndGetIndex(resume.getUuid());
        doSave(resume, index);
    }

    @Override
    public final void update(Resume resume) {
        log.info("Update " + resume);
        final T index = checkResumeExistsAndGetIndex(resume.getUuid());
        doUpdate(index, resume);
    }

    @Override
    public final void delete(String uuid) {
        log.info("Delete " + uuid);
        final T index = checkResumeExistsAndGetIndex(uuid);
        doDelete(index);
    }

    @Override
    public final Resume get(String uuid) {
        log.info("Get " + uuid);
        final T index = checkResumeExistsAndGetIndex(uuid);
        return doGet(index);
    }

    @Override
    public List<Resume> getAllSorted() {
        log.info("getAllSorted");
        List<Resume> resumeList = doGetAll();
        resumeList.sort(RESUME_COMPARATOR_BY_NAME);
        return resumeList;
    }


    private T checkResumeDoesntExistAndGetIndex(String uuid) {
        T index = getIndex(uuid);
        if (exists(index)) {
            log.warning("Resume with id=" + uuid + " already exists in the storage.");
            throw new ResumeExistsInStorageException(uuid);
        }
        return index;
    }


    private T checkResumeExistsAndGetIndex(String uuid) {
        T index = getIndex(uuid);
        if (!exists(index)) {
            log.warning("Resume with id=" + uuid + " doesn't exist in the storage.");
            throw new ResumeDoesntExistInStorageException(uuid);
        }
        return index;
    }

}
