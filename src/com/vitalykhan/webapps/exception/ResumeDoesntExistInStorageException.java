package com.vitalykhan.webapps.exception;

public class ResumeDoesntExistInStorageException extends StorageException {

    public ResumeDoesntExistInStorageException(String uuid) {
        super("Resume with id=" + uuid + " doesn't exist in the storage.", uuid);
    }
}
