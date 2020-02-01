package com.vitalykhan.webapps.model;

public enum SectionType {
    OBJECTIVE ("Цель"),
    PERSONAL ("Личные качества"),
    LANGUAGES ("Языки"),
    ACHIEVEMENT ("Достижения"),
    QUALIFICATIONS ("Квалификация"),
    HOBBIES ("Хобби"),
    EXPERIENCE ("Опыт работы"),
    EDUCATION ("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
