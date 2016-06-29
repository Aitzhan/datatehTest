package com.aitzhan.datatehTest.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee", schema = "public", catalog = "postgres")
public class EmployeeEntity  implements Serializable {
    private long employeeId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private SubdivisionEntity subdivision;

    @Id
    @GeneratedValue
    @Column(name = "employee_id", nullable = false)
    public long getEmployeeId() {
        return employeeId;
    }

    private void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 20)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 20)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    @Basic
    @Column(name = "patronymic", nullable = true, length = 20)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Column(name = "position", nullable = true, length = 20)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @ManyToOne
    @JoinColumn(name="SUBDIVISION_ID")
    public SubdivisionEntity getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(SubdivisionEntity subdivision) {
        this.subdivision = subdivision;
    }

    public EmployeeEntity() {}

    public EmployeeEntity(String firstName, String lastName, String patronymic, String position, SubdivisionEntity subdivision) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.position = position;
        this.subdivision = subdivision;
    }
}
