package com.vitalykhan.webapps;

import com.vitalykhan.webapps.storage.SqlStorage;
import com.vitalykhan.webapps.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("./config/resumes.properties");
    private static final Config instance = new Config();

    private final File storageDir;
    private final Storage storage;

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            storage = new SqlStorage(properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));

        } catch (IOException ex) {
            throw new IllegalStateException("Invalid config file " + PROPS.getName());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    public static Config get() {
        return instance;
    }

}
