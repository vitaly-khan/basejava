package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}