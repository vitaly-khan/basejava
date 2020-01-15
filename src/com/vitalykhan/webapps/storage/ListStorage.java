package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> resumeList = new ArrayList<>();

    @Override
    void checkNoStorageOverflow(Resume resume) {
        /*No need to check size in ListStorage*/
    }

    @Override
    boolean exists(Integer index) {
        return index != null;
    }

    @Override
    Integer getIndex(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    void doSave(Resume resume, Integer index) {
        resumeList.add(resume); //we don't care about index in ListStorage. New element is added to the end.
    }

    @Override
    protected void doUpdate(Integer index, Resume resume) {
        resumeList.set(index, resume);
    }

    @Override
    void doDelete(Integer index) {
        resumeList.remove((int) index);
    }

    @Override
    Resume doGet(Integer index) {
        return resumeList.get(index);
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
