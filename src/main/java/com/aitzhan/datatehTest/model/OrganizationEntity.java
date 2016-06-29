package com.aitzhan.datatehTest.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Aitzhan on 05.06.2016.
 */
@Entity
@Table(name = "ORGANIZATION")
public class OrganizationEntity implements Serializable{
    private long organizationId;
    private String title;
    private String physicalAddress;
    private String juridicalAddress;
    private EmployeeEntity leader;

    public OrganizationEntity() {}

    public OrganizationEntity(String title, String physicalAddress, String juridicalAddress, EmployeeEntity leader) {
        this.title = title;
        this.physicalAddress = physicalAddress;
        this.juridicalAddress = juridicalAddress;
        this.leader = leader;
    }

    @Id
    @Column(name = "ORGANIZATION_ID", nullable = false)
    public long getOrganizationId() {
        return organizationId;
    }

    private void setOrganizationId(long organization_id) {
        this.organizationId = organization_id;
    }

    @Basic
    @Column(name = "TITLE", length = 50)
    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    @Basic
    @Column(name = "PHYSICAL_ADDRESS", length = 200)
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    @Basic
    @Column(name = "JURIDICAL_ADDRESS",  length = 200)
    public String getJuridicalAddress() {
        return juridicalAddress;
    }

    public void setJuridicalAddress(String juridicalAddress) {
        this.juridicalAddress = juridicalAddress;
    }

    @OneToOne
    @JoinColumn(name = "LEADER")
    public EmployeeEntity getLeader() {
        return leader;
    }

    public void setLeader(EmployeeEntity leader) {
        this.leader = leader;
    }
}
