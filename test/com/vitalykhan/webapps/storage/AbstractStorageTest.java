package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractStorageTest {
    Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void beforeMethod() {
        storage.clear();
        storage.save(new Resume("1"));
        storage.save(new Resume("5"));
        storage.save(new Resume("3"));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume("3"));
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        storage.save(new Resume("7"));
        Assert.assertEquals(4, storage.size());
        storage.get("7");
    }

    @Test(expected = ResumeExistsInStorageException.class)
    public void saveExists() {
        storage.save(new Resume("3"));
    }

    @Test
    public void getAll() {
        Assert.assertTrue(storage.size() == 3);
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume("1"), storage.get("1"));
        Assert.assertEquals(new Resume("3"), storage.get("3"));
        Assert.assertEquals(new Resume("5"), storage.get("5"));
    }


    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void delete() {
        storage.delete("1");
        Assert.assertEquals(2, storage.size());
        storage.get("1");
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void deleteNotExist() {
        storage.delete("9");
    }

}