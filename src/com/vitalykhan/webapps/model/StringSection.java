package com.vitalykhan.webapps.model;

import java.util.Objects;

public class StringSection extends Section {
    private String content; //It's final at Grigory's

    public StringSection(String content) {
        Objects.requireNonNull(content, "Section mustn't be null");
        this.content = content;

    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringSection that = (StringSection) o;

        return content.equals(that.content);

    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
