package com.vitalykhan.webapps.storage.serializer;

import com.vitalykhan.webapps.model.Resume;
import com.vitalykhan.webapps.utils.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyJsonSerializer implements Serializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(r, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}