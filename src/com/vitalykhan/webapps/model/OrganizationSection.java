package com.vitalykhan.webapps.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private List<Organization> organizationList;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "Organizations mustn't be null");
        this.organizationList = organizationList;
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public String toString() {
        return organizationList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizationList.equals(that.organizationList);

    }

    @Override
    public int hashCode() {
        return organizationList.hashCode();
    }
}
