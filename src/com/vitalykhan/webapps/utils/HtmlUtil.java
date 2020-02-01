package com.vitalykhan.webapps.utils;

import com.vitalykhan.webapps.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HtmlUtil {
    public static String getFullReference(ContactType ct, String value) {
        if (ct.getRef() == null) return "" + value;
        return ((ct.getRef() == null) || (value == null)) ? ""
                : String.format("<a href=\"%s%s\">%2$s</a>", ct.getRef(), value);
    }

    public static List<String> sectionToHtmlList(Section section) {
        if (section instanceof StringSection) {
            return Arrays.asList(((StringSection) section).getContent());
        } else if (section instanceof ListSection) {
            return ((ListSection) section).getItems();
        }
        return new ArrayList<>();
    }

    public static String getContent(SectionType type, Resume resume) {
        Section section = resume.getSection(type);
        return section == null ? "" : ((StringSection) resume.getSection(type)).getContent();
    }

    public static String getItems(SectionType type, Resume resume) {
        Section section = resume.getSection(type);
        return
                section == null ? "" :
                        String.join("\n", ((ListSection) resume.getSection(type)).getItems());
    }
}
