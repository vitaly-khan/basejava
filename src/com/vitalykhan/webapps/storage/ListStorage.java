package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumeList = new ArrayList<>();

    @Override
    void checkNoStorageOverflow(Resume resume) {
        /*No need to check size in ListStorage*/
    }

    @Override
    boolean exists(Object index) {
        return index != null;
    }

    @Override
    Object getIndex(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    void doSave(Resume resume, Object index) {
        resumeList.add(resume); //we don't care about index in ListStorage. New element is added to the end.
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        resumeList.set((int) index, resume);
    }

    @Override
    void doDelete(Object index) {
        resumeList.remove((int) index);
    }

    @Override
    Resume doGet(Object index) {
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
    List<Resume> doGetAll() {
        return new ArrayList<>(resumeList);
    }
}
