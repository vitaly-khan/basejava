package com.vitalykhan.webapps.storage.serializer;

import com.vitalykhan.webapps.model.*;
import com.vitalykhan.webapps.utils.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializer implements Serializer {
    private XmlParser xmlParser;

    public XmlSerializer() {
        this.xmlParser = new XmlParser(Resume.class, Section.class,
                ListSection.class, OrganizationSection.class, StringSection.class,
                Organization.class, Organization.Position.class, Link.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream index) throws IOException {
        try (Writer writer = new OutputStreamWriter(index, StandardCharsets.UTF_8)) {
            xmlParser.marshal(resume, writer);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshal(reader);
        }
    }
}
