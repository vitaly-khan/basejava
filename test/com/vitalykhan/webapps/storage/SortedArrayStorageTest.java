package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.model.Resume;
import org.junit.Assert;

public class SortedArrayStorageTest extends AbstractArrayStorageTest{
    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Override
    public void getAll() {
        Resume[] result = storage.getAll();
        Assert.assertEquals(new Resume("1"), result[0]);
        Assert.assertEquals(new Resume("3"), result[1]);
        Assert.assertEquals(new Resume("5"), result[2]);
    }
}
