package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;
import org.junit.Assert;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Override
    public void getAll() {
        Resume[] result = storage.getAll();
        Assert.assertEquals(new Resume("1"), result[0]);
        Assert.assertEquals(new Resume("5"), result[1]);
        Assert.assertEquals(new Resume("3"), result[2]);
    }
}
