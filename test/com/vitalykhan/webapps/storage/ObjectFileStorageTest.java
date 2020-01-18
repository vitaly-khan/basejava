package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.storage.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(new ObjectStreamSerializer(), WORKING_DIR));
    }
}