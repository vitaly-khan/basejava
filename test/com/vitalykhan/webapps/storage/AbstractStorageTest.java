package com.vitalykhan.webapps.storage;

import com.vitalykhan.webapps.Config;
import com.vitalykhan.webapps.ResumeTestData;
import com.vitalykhan.webapps.exception.ResumeDoesntExistInStorageException;
import com.vitalykhan.webapps.exception.ResumeExistsInStorageException;
import com.vitalykhan.webapps.model.ContactType;
import com.vitalykhan.webapps.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File WORKING_DIR = Config.get().getStorageDir();
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
        assertEquals(0, storage.size());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
        storage.clear();
        assertEquals(0, storage.size());

    }

    @Test
    public void update() {
        Resume newResume = new Resume(ResumeTestData.UUID2, "Updated Regina");
        newResume.addContact(ContactType.PHONE_NUMBER, "123-123-123");
        newResume.addContact(ContactType.EMAIL, "vvv@rrr.ru");
        storage.update(newResume);
        assertEquals(newResume, storage.get(ResumeTestData.UUID2));
        assertEquals(3, storage.size());
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        storage.save(new Resume("7", "Natasha"));
        assertEquals(4, storage.size());
        storage.get(ResumeTestData.UUID1);
        storage.get("7");
    }

    @Test(expected = ResumeExistsInStorageException.class)
    public void saveExists() {
        storage.save(new Resume(ResumeTestData.UUID3, ResumeTestData.UUID3));
    }

    @Test
    public void getAllSorted() {
        List<Resume> resumeList = storage.getAllSorted();
        assertEquals(3, resumeList.size());
        assertEquals(Arrays.asList(ResumeTestData.R2, ResumeTestData.R1, ResumeTestData.R3), resumeList);
    }

    @Test
    public void get() {
        assertEquals(ResumeTestData.R1, storage.get(ResumeTestData.R1.getUuid()));
        assertEquals(ResumeTestData.R2, storage.get(ResumeTestData.R2.getUuid()));
        assertEquals(ResumeTestData.R3, storage.get(ResumeTestData.R3.getUuid()));
    }


    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void delete() {

        try {
            storage.delete(ResumeTestData.UUID1);
        } catch (Exception e) {
            Assert.fail();
        }
        assertEquals(2, storage.size());
        storage.get("1");
    }

    @Test(expected = ResumeDoesntExistInStorageException.class)
    public void deleteNotExist() {
        storage.delete("9");
    }

}