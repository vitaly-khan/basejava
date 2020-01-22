package com.vitalykhan.webapps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("./config/resumes.properties");
    private static final Config instance = new Config();
    private Properties properties = new Properties();
    private File storageDir;

    public File getStorageDir() {
        return storageDir;
    }

    public static Config get() {
        return instance;
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUser() {
        return properties.getProperty("db.user");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
