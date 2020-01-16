package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory mustn't be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    void checkNoStorageOverflow(Resume resume) {
        //No need to check available space
    }

    @Override
    void doSave(Resume resume, File index) {
        try {
            index.createNewFile();
            doWrite(resume, index);
        } catch (IOException e) {
            throw new StorageException("IO Exception", resume.getUuid(), e);
        }
    }

    abstract void doWrite(Resume resume, File index);

    @Override
    void doUpdate(File index, Resume resume) {
            doWrite(resume, index);
    }

    @Override
    void doDelete(File index) {
        index.delete();
    }

    @Override
    Resume doGet(File index) {

        return doRead(index);
    }

    abstract Resume doRead(File index);

    @Override
    List<Resume> doGetAll() {
        List<Resume> result = new ArrayList<>();
        for (File file : checkFileExistsAndGetFiles()) {
            result.add(doRead(file));
        }

        return result;
    }

    @Override
    boolean exists(File index) {
        return index.exists();
    }

    @Override
    File getIndex(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        for (File file : checkFileExistsAndGetFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return checkFileExistsAndGetFiles().length;
    }

    private File[] checkFileExistsAndGetFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO Exception", "no uuid");
        }
        return files;
    }
}
