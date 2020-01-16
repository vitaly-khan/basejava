package com.vitalykhan.webapps;

import com.vitalykhan.webapps.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("1", "Vitaly Khan");

        String phone = "+7-910-1234567";

        Section achievements = new StringSection("a@b.ru");

        List<Organization> organizationList = new ArrayList<>();
        Organization lastOrganization = new Organization(
                "Taxi", "taxi-2000.com", LocalDate.of(2012, 01, 01),
                LocalDate.of(2020, 01, 01), "Управляющий",
                "Выполнял широкий спектр обязанностей: и менеджера, и программиста, и HR");
        Organization firstOrganization = new Organization(
                "НИ РХТУ им. Д.И.Менделеева", null, LocalDate.of(2006, 01, 01),
                LocalDate.of(2012, 01, 01), "Ассистент кафедры",
                "Преподавал информатику, в т.ч. основы программирования");
        organizationList.add(lastOrganization);
        organizationList.add(firstOrganization);
        Section experience = new OrganizationSection(organizationList);

        Map<ContactType, String> contactsMap = resume.getContactsMap();
        Map<SectionType, Section> sectionsMap = resume.getSectionsMap();
        contactsMap.put(ContactType.PHONE_NUMBER, phone);
        sectionsMap.put(SectionType.ACHIEVEMENT, achievements);
        sectionsMap.put(SectionType.EXPERIENCE, experience);
        System.out.println(resume);


    }
}
