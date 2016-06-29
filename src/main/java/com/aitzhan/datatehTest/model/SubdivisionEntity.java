package com.aitzhan.datatehTest.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SUBDIVISION")
public class SubdivisionEntity implements Serializable{
    private long subdivisionId;
    private String title;
    private String contacts;
    private EmployeeEntity leader;
    private OrganizationEntity organization;

    public SubdivisionEntity() {}

    public SubdivisionEntity(String title, String contacts, EmployeeEntity leader, OrganizationEntity organization) {
        this.title = title;
        this.contacts = contacts;
        this.leader = leader;
        this.organization = organization;
    }

    @Id
    @GeneratedValue
    @Column(name = "SUBDIVISION_ID", nullable = false)
    public long getSubdivisionId() {
        return subdivisionId;
    }

    private void setSubdivisionId(long subdivision_id) {
        this.subdivisionId = subdivision_id;
    }

    @Basic
    @Column(length = 20)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(length = 100)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @ManyToOne
    @JoinColumn(name = "leader")
    public EmployeeEntity getLeader() {
        return leader;
    }

    public void setLeader(EmployeeEntity leader) {
        this.leader = leader;
    }

    @ManyToOne
    @JoinColumn(name = "ORGANIZATION_ID")
    public OrganizationEntity getOrganization() {return organization;}

    public void setOrganization(OrganizationEntity organization) {this.organization = organization;}
}
