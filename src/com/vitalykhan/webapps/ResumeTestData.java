package com.vitalykhan.webapps;

import com.vitalykhan.webapps.model.*;

import java.util.UUID;

public class ResumeTestData {
    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final String UUID1 = UUID.randomUUID().toString();
    public static final String UUID2 = UUID.randomUUID().toString();
    public static final String UUID3 = UUID.randomUUID().toString();

    static {
        R1 = new Resume(UUID1, "Name 2");
        R2 = new Resume(UUID2, "Name 1");
        R3 = new Resume(UUID3, "Name 3");


        R1.addContact(ContactType.PHONE_NUMBER, "+7-910-1234567");
        R1.addContact(ContactType.GITHUB, "http://www.github.com/vitaly-khan");
        R1.addContact(ContactType.EMAIL, "a@b.ru");
        R2.addContact(ContactType.EMAIL, "regina@b.ru");

        R1.addSection(SectionType.PERSONAL, new StringSection(
                "хорошо развитое математическое (логическое) мышление, грамотность, ответственность, уравновешенность"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection(
                "Achievement1", "Achievement2", "Achievement3"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java Core", "HTML, SQL", "JDBC, Spring, Hibernate"));

//
//        R1.addSection(SectionType.EDUCATION, new OrganizationSection(
//                new Organization(
//                        "НИ РХТУ им.Д.И.Мендеелеева", null,
//                        DateUtil.of(2001, Month.SEPTEMBER),
//                        DateUtil.of(2006, Month.MAY),
//                        "Дневное обучение, Автоматизированные системы ОИУ",
//                        "")
//        ));
//        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
//                new Organization(
//                        "НИ РХТУ им.Д.И.Менделеева", null,
//                        new Organization.Position(
//                                DateUtil.of(2006, Month.MAY),
//                                DateUtil.of(2008, Month.SEPTEMBER),
//                                "Ведущий программист",
//                                "В основном занимался системным администрированием"
//                        ),
//                        new Organization.Position(
//                                DateUtil.of(2008, Month.SEPTEMBER),
//                                DateUtil.of(2010, Month.MAY),
//
//                                "Ассистент кафедры",
//                                "Преподавал информатику, в т.ч. основы программирования"
//                                )
//                ),
//                new Organization("ИП Храпов (Служба такси)", "", new Organization.Position(
//                        DateUtil.of(2010, Month.MAY),
//                        "управляющий",
//                        "Выполнял широкий спектр обязанностей: и менеджера, и программиста, и HR"))
//        ));
    }
}
