package com.aitzhan.datatehTest.logic;

public interface OrganizationStructure {
    String getOrganizationJSON(long id);
    String getSubdivisionsListJSON(long organizationId);
    String getSubdivisionJSON(long subdivisionId);
    boolean updateSubdivision(String subdivisionJSON);
    boolean addSubdivision(String subdivisionJSON);
    boolean deleteSubdivision(long id);
}
