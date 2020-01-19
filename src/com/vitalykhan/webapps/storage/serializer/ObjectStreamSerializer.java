package com.vitalykhan.webapps.storage.serializer;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements Serializer {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Class Resume not found", "", e);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream index) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(index)) {
            os.writeObject(resume);
        }
    }
}
