package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;
import com.vitalykhan.webapps.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;

    private StreamSerializer streamSerializer;

    protected FileStorage(StreamSerializer streamSerializer, File directory) {
        Objects.requireNonNull(directory, "directory mustn't be null");

        this.streamSerializer = streamSerializer;
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
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + index.getAbsolutePath(), resume.getUuid(), e);
        }
        doUpdate(index, resume);
    }

    @Override
    void doUpdate(File index, Resume resume) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(index)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    void doDelete(File index) {
        if (!index.delete()) {
            throw new StorageException("File delete error", index.getName());
        }
    }

    @Override
    Resume doGet(File index) {

        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(index)));
        } catch (IOException e) {
            throw new StorageException("File read error", index.getName(), e);
        }
    }


    @Override
    List<Resume> doGetAll() {
        List<Resume> result = new ArrayList<>();
        for (File file : checkFileExistsAndGetFiles()) {
            result.add(doGet(file));
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
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return checkFileExistsAndGetFiles().length;
    }

    private File[] checkFileExistsAndGetFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        return files;
    }
}
