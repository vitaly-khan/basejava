package com.vitalykhan.webapps.model;

import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>, Serializable {

    // Unique identifier
    private final String uuid;

    private String fullName;

    private final Map<ContactType, String> contactsMap = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sectionsMap = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid,"uuid mustn't be null");
        Objects.requireNonNull(fullName,"fullName mustn't be null");
        this.uuid = uuid;
        this.fullName = fullName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Resume resume) {
        int result = this.getFullName().compareTo(resume.getFullName());
        return result != 0 ? result : this.uuid.compareTo(resume.uuid);
    }
}
