package com.vitalykhan.webapps.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StorageTest.class,
        SortedStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        XmlPathStorageTest.class,
        JsonPathStorageTest.class
})
public class AllStoragesTest {

}
