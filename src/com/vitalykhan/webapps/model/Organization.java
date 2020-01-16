package com.vitalykhan.webapps.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link nameWithUrl;
    private final List<Position> positionList;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate mustn't be null");
        Objects.requireNonNull(endDate, "endDate mustn't be null");
        Objects.requireNonNull(title, "title mustn't be null");
        nameWithUrl = new Link(name, url);
        positionList = new ArrayList<>();
        positionList.add(new Position(startDate, endDate, title, description));
    }

    public Organization(String name, String url, List<Position> positions) {
        Objects.requireNonNull(positions, "Positions mustn't be null in constructor");
        nameWithUrl = new Link(name, url);
        positionList = positions;
    }

    public Link getNameWithUrl() {
        return nameWithUrl;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "nameWithUrl=" + nameWithUrl +
                ", positionList=" + positionList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!nameWithUrl.equals(that.nameWithUrl)) return false;
        return positionList.equals(that.positionList);

    }

    @Override
    public int hashCode() {
        int result = nameWithUrl.hashCode();
        result = 31 * result + positionList.hashCode();
        return result;
    }
}
