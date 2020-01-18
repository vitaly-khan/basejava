package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.storage.serializer.ObjectStreamStorage;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(new ObjectStreamStorage(), WORKING_DIR.toString()));
    }
}