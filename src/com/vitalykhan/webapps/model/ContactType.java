package com.vitalykhan.webapps.model;

public enum ContactType {
    PHONE_NUMBER("Номер телефона:"),
    EMAIL("E-Mail"),
    GITHUB("Аккаунт на Гитхабе");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
