package com.vitalykhan.webapps.storage.serializer;

import com.vitalykhan.webapps.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int contactsMapSize = dis.readInt();
            for (int i = 0; i < contactsMapSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionsMapSize = dis.readInt();
            for (int i = 0; i < sectionsMapSize; i++) {
                SectionType sectionMapKey = SectionType.valueOf(dis.readUTF());
                switch (sectionMapKey) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionMapKey, new StringSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSectionSize = dis.readInt();
                        List<String> items = new ArrayList<>(listSectionSize);
                        for (int j = 0; j < listSectionSize; j++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(sectionMapKey, new ListSection(items));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        int organizationListSize = dis.readInt();
                        List<Organization> organizationList = new ArrayList<>(organizationListSize); //little optimization
                        for (int j = 0; j < organizationListSize; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            List<Organization.Position> positionList = new ArrayList<>();
                            int positionListSize = dis.readInt();
                            for (int k = 0; k < positionListSize; k++) {
                                String str;
                                positionList.add(new Organization.Position(
                                        LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()),
                                        dis.readUTF(),
                                        (str = dis.readUTF()).equals("null") ? null : str
                                ));
                            }
                            organizationList.add(new Organization(name, url.equals("null") ? null : url, positionList));
                        }
                        resume.addSection(sectionMapKey, new OrganizationSection(organizationList));
                }
            }
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream index) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(index)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contactsMap = resume.getContactsMap();
            dos.writeInt(contactsMap.size());
            for (Map.Entry<ContactType, String> entry : contactsMap.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sectionsMap = resume.getSectionsMap();
            dos.writeInt(sectionsMap.size());
            for (Map.Entry<SectionType, Section> entry : sectionsMap.entrySet()) {
                SectionType key = entry.getKey();
                dos.writeUTF(key.name());
                switch (key) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((StringSection) resume.getSection(key)).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = ((ListSection) resume.getSection(key)).getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizationList = ((OrganizationSection) resume.getSection(key)).getOrganizationList();
                        dos.writeInt(organizationList.size());
                        for (Organization organization : organizationList) {
                            dos.writeUTF(organization.getNameWithUrl().getName());
                            String url = organization.getNameWithUrl().getUrl();
                            dos.writeUTF(url == null ? "null" : url);
                            List<Organization.Position> positionList = organization.getPositionList();
                            dos.writeInt(positionList.size());
                            for (Organization.Position position : positionList) {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                dos.writeUTF(description == null ? "null" : description); //what if user entered "null" here?
                            }
                        }
                }
            }
        }
    }
}
