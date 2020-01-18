package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(new ObjectStreamSerializer(), WORKING_DIR.toString()));
    }
}