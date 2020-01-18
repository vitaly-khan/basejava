package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.storage.serializer.ObjectStreamStorage;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(new ObjectStreamStorage(), WORKING_DIR));
    }
}