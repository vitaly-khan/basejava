package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumeList = new ArrayList<>();

    @Override
    void checkSizeWhenSaving(Resume resume) {
        /*No need to check size in ListStorage*/
    }

    @Override
    Object checkResumeDoesntExistAndGetIndex(Resume resume) {
        for (Resume r : resumeList) {
            if (r.getUuid().equals(resume.getUuid())) {
                throw new ResumeExistsInStorageException(resume.getUuid());
            }
        }
        return null; //we can return null or anything else here... it doesn't affect program.
    }

    @Override
    void saveProcessing(Resume resume, Object index) {
        resumeList.add(resume); //we don't care about index in ListStorage. New element is added to the end.
    }


    @Override
    Object checkResumeExistsAndGetIndex(Resume resume) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        throw new ResumeDoesntExistInStorageException(resume.getUuid());
    }

    @Override
    protected void updateProcessing(Object index, Resume resume) {
        resumeList.set((int) index, resume);
    }

    @Override
    void deleteProcessing(Object index) {
        resumeList.remove((int) index);
    }

    @Override
    Resume getResume(Object index) {
        return resumeList.get((int) index);
    }


    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[0]);
    }
}
