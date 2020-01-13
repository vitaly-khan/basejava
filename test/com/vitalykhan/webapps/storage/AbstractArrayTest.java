package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.StorageException;
import com.vitalykhan.webapps.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public abstract class AbstractArrayTest extends AbstractStorageTest{
    public AbstractArrayTest(Storage storage) {
        super(storage);
    }
    @Test(expected = StorageException.class)
    public void saveTooMany() {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(UUID.randomUUID().toString()));
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume("last"));
    }
}
