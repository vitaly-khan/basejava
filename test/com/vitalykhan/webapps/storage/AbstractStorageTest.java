package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.ResumeTestData;
import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final File WORKING_DIR = new File("c:/Java/Project/basejava/storage");
    Storage storage;

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
        Resume newResume = new Resume(ResumeTestData.UUID2, "Updated Regina");
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(ResumeTestData.UUID2));
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
        List<Resume> resumeList = storage.getAllSorted();
        Assert.assertEquals(3, resumeList.size());
        Assert.assertEquals(resumeList, Arrays.asList(ResumeTestData.R1, ResumeTestData.R2, ResumeTestData.R3));
    }

    @Test
    public void get() {
        Assert.assertEquals(ResumeTestData.R1, storage.get(ResumeTestData.R1.getUuid()));
        Assert.assertEquals(ResumeTestData.R2, storage.get(ResumeTestData.R2.getUuid()));
//        Assert.assertEquals(ResumeTestData.R3, storage.get(ResumeTestData.R3.getUuid()));
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