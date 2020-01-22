package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.storage.serializer.MyJsonSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(new MyJsonSerializer(), WORKING_DIR.toString()));
    }
}