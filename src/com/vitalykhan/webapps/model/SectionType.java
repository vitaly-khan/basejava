package com.vitalykhan.webapps.model;

public enum SectionType {
    OBJECTIVE ("Цель"),
    PERSONAL ("Личные качества"),
    ACHIEVEMENT ("Достижения"),
    QUALIFICATIONS ("Квалификация"),
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
