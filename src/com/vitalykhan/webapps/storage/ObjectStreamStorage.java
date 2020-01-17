package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    Resume doRead(InputStream is) throws IOException {
        Resume resume = null;
        try(ObjectInputStream ois = new ObjectInputStream(is)) {
            resume = (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Class Resume not found", "", e);
        }
        return resume;
    }

    @Override
    void doWrite(Resume resume, OutputStream index) throws IOException {
        try(ObjectOutputStream os = new ObjectOutputStream(index)) {
            os.writeObject(resume);
        }
    }
}