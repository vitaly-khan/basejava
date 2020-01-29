package com.vitalykhan.webapps.utils;

import com.vitalykhan.webapps.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HtmlUtil {
    public static String getFullReference(ContactType ct, String value) {
        if (ct.getRef() == null) return "" + value;
        return ((ct.getRef() == null) || (value == null)) ? ""
                : String.format("<a href=\"%s:%s\">%2$s</a>", ct.getRef(), value);
    }

    public static List<String> sectionToHtmlList(Section section) {
        if (section instanceof StringSection) {
            return Arrays.asList(((StringSection) section).getContent());
        } else if (section instanceof ListSection) {
            return ((ListSection) section).getItems();
        }
        return new ArrayList<>();
    }

    public static List<String> sectionToHtmlList(SectionType type, Section section) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                return section == null ? Arrays.asList("") : Arrays.asList(((StringSection) section).getContent());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> list = new ArrayList<>(5);
                if (section != null) {
                    list = ((ListSection) section).getItems();
                }
                int size = list.size();
                for (int i = 0; i < 5 - size; i++) {
                    list.add("");
                }
                return list;
            case EXPERIENCE:
            case EDUCATION: //Falling through
            default:
                return Collections.emptyList();
        }
    }

}
