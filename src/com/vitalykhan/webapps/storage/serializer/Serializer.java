package com.vitalykhan.webapps.storage.serializer;

import com.vitalykhan.webapps.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
    void doWrite(Resume resume, OutputStream index) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
