package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected AbstractPathStorage(String dir) {
        this.directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory mustn't be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(Paths.get(dir) + " is not directory or is not writable");
        }
    }

    @Override
    void checkNoStorageOverflow(Resume resume) {
        //No need to check available space
    }

    @Override
    void doSave(Resume resume, Path index) {
        try {
            Files.createFile(index);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + index, resume.getUuid(), e);
        }
        doUpdate(index, resume);

    }

    abstract Resume doRead(InputStream is) throws IOException;

    abstract void doWrite(Resume resume, OutputStream index) throws IOException;

    @Override
    void doUpdate(Path index, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(index)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    void doDelete(Path index) {
        try {
            Files.delete(index);
        } catch (IOException e) {
            throw new StorageException("File delete error", index.toString());
        }
    }

    @Override
    Resume doGet(Path index) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(index)));
        } catch (IOException e) {
            throw new StorageException("File read error", index.toString(), e);
        }
    }


    @Override
    List<Resume> doGetAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    boolean exists(Path index) {
        return Files.exists(index);
    }

    @Override
    Path getIndex(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Reading directory error", null, e);
        }
    }
}

