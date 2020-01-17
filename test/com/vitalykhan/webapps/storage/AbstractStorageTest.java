package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.ResumeTestData;
import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public abstract class AbstractStorageTest {
    Storage storage;

    {}

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void beforeMethod() {
        storage.clear();
        storage.save(ResumeTestData.R1);
        storage.save(ResumeTestData.R2);
        storage.save(ResumeTestData.R3);
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
        storage.update(new Resume("1","Regina"));
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        storage.save(new Resume("7", "Natasha"));
        Assert.assertEquals(4, storage.size());
        storage.get("7");
    }

    @Test(expected = ResumeExistsInStorageException.class)
    public void saveExists() {
        storage.save(new Resume(ResumeTestData.UUID3, ResumeTestData.UUID3));
    }

    @Test
    public void getAllSorted() {
        Assert.assertEquals(3, storage.size());
        List<Resume> resumeList = storage.getAllSorted();
        Assert.assertEquals("Regina", resumeList.get(0).getFullName());
        Assert.assertEquals("Timur", resumeList.get(1).getFullName());
        Assert.assertEquals("Vitaly Khan", resumeList.get(2).getFullName());
    }

    @Test
    public void get() {
        Assert.assertEquals("Vitaly Khan", storage.get("1").getFullName());
        Assert.assertEquals("Regina", storage.get("2").getFullName());
        Assert.assertEquals("Timur", storage.get("3").getFullName());
    }


    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void delete() {

        try {
            storage.delete("1");
        } catch (Exception e) {
            Assert.fail();
        }
        Assert.assertEquals(2, storage.size());
        storage.get("1");
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void deleteNotExist() {
        storage.delete("9");
    }

}