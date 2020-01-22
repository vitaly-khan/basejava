package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(new DataStreamSerializer(), WORKING_DIR.toString()));
    }
}
