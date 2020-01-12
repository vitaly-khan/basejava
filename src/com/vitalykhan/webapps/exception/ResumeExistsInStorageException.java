package com.vitalykhan.webapps.exception;

public class ResumeExistsInStorageException extends StorageException {

    public ResumeExistsInStorageException(String uuid) {
        super("Resume with id=" + uuid + " already exists in the storage.", uuid);
    }
}
