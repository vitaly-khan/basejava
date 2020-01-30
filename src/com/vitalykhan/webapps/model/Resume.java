package com.vitalykhan.webapps.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    // Unique identifier
    private String uuid;

    private String fullName;

    private final Map<ContactType, String> contactsMap = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sectionsMap = new EnumMap<>(SectionType.class);

    public static final Resume EMPTY = new Resume();

    public Resume() {}

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid,"uuid mustn't be null");
        Objects.requireNonNull(fullName,"fullName mustn't be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContactsMap() {
        return contactsMap;
    }

    public Map<SectionType, Section> getSectionsMap() {
        return sectionsMap;
    }

    public String getContact(ContactType type) {
        return contactsMap.get(type);
    }

    public Section getSection(SectionType type) {
        return sectionsMap.get(type);
    }

    public void addContact(ContactType type, String string) {
        contactsMap.put(type, string);
    }

    public void addSection(SectionType type, Section section) {
        sectionsMap.put(type, section);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contactsMap, resume.contactsMap) &&
                Objects.equals(sectionsMap, resume.sectionsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactsMap, sectionsMap);
    }

    @Override
    public int compareTo(Resume resume) {
        int result = this.getFullName().compareTo(resume.getFullName());
        return result != 0 ? result : this.uuid.compareTo(resume.uuid);
    }
}
