package com.vitalykhan.webapps.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private final Link nameWithUrl;
    private final List<Position> positionList;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        nameWithUrl = new Link(name, url);
        positionList = new ArrayList<>();
        positionList.add(new Position(startDate, endDate, title, description));
    }

    public Organization(String name, String url, List<Position> positions) {
        Objects.requireNonNull(positions, "Positions mustn't be null in constructor");
        nameWithUrl = new Link(name, url);
        positionList = positions;
    }

    public Organization(String name, String url, Position... positions) {
        this(name, url, Arrays.asList(positions));
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
        return nameWithUrl.equals(that.nameWithUrl) &&
                positionList.equals(that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameWithUrl, positionList);
    }

    public static class Position implements Serializable {
        private LocalDate startDate;
        private LocalDate endDate;
        private String title;
        private String description;

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate mustn't be null");
            Objects.requireNonNull(endDate, "endDate mustn't be null");
            Objects.requireNonNull(title, "title mustn't be null");

            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public Position(LocalDate startDate, String title, String description) {
            this(startDate, LocalDate.now(), title, description);
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
