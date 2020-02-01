package com.vitalykhan.webapps.model;

public enum ContactType {
    PHONE_NUMBER("Номер телефона", null),
    EMAIL("E-Mail", "mailto:"),
    GITHUB("GitHub", ""),
    LINKEDIN("LinkedIn", ""),
    SKYPE("Skype", "skype:");

    private String title;
    private String ref;

    public String getTitle() {
        return title;
    }

    public String getRef() {
        return ref;
    }

    ContactType(String title, String ref) {
        this.title = title;
        this.ref = ref;
    }
}
