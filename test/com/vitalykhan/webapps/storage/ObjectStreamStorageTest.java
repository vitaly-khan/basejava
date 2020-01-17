package com.vitalykhan.webapps.storage;

import static org.junit.Assert.*;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(WORKING_DIR));
    }
}