package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.storage.serializer.XmlSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(new XmlSerializer(), WORKING_DIR.toString()));
    }
}