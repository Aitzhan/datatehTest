package com.aitzhan.datatehTest.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Aitzhan on 05.06.2016.
 */

@Entity
@Table(name = "ORDERS")
public class OrderEntity implements Serializable {
    private long orderId;
    private String subject;
    private EmployeeEntity author;
    private EmployeeEntity executor;
    private Date periodOfExecution;
    private String signOfControl;
    private String signOfExecution;
    private String text;
    private ExecutionState status;

    public OrderEntity() {}

    public OrderEntity(String subject, EmployeeEntity author, Date periodOfExecution, String signOfControl, EmployeeEntity executor, String signOfExecution, String text) {
        this.subject = subject;
        this.author = author;
        this.periodOfExecution = periodOfExecution;
        this.signOfControl = signOfControl;
        this.executor = executor;
        this.signOfExecution = signOfExecution;
        this.text = text;
        this.status = ExecutionState.IN_EXECUTION;
    }

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long id) {
        this.orderId = id;
    }

    @Basic
    @Column(length = 100)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @ManyToOne
    @JoinColumn(name = "AUTHOR")
    public EmployeeEntity getAuthor() {
        return author;
    }

    public void setAuthor(EmployeeEntity author) {
        this.author = author;
    }

    @ManyToOne
    @JoinColumn(name = "EXECUTOR")
    public EmployeeEntity getExecutor() {
        return executor;
    }

    public void setExecutor(EmployeeEntity executor) {
        this.executor = executor;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PERIOD_OF_EXECUTION")
    public Date getPeriodOfExecution() {
        return periodOfExecution;
    }

    public void setPeriodOfExecution(Date periodOfExecution) {
        this.periodOfExecution = periodOfExecution;
    }

    @Basic
    @Column(name="SIGN_OF_CONTROL", length = 200)
    public String getSignOfControl() {
        return signOfControl;
    }

    public void setSignOfControl(String signOfControl) {
        this.signOfControl = signOfControl;
    }

    @Basic
    @Column(name = "SIGN_OF_EXECUTION", length = 200)
    public String getSignOfExecution() {
        return signOfExecution;
    }

    public void setSignOfExecution(String signOfExecution) {
        this.signOfExecution = signOfExecution;
    }

    @Basic
    @Column(length = 2000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Enumerated(EnumType.ORDINAL)
    public ExecutionState getStatus() {
        return status;
    }

    public void setStatus(ExecutionState status) {
        this.status = status;
    }
}
