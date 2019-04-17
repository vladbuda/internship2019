package com.internship.coera.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.List;

public class Employee {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date employmentStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date employmentEndDate;
    private List<SuspensionPeriod> suspensionPeriodList;

    public Employee(Date employmentStartDate, Date employmentEndDate, List<SuspensionPeriod> suspensionPeriodList) {
        this.employmentStartDate = employmentStartDate;
        this.employmentEndDate = employmentEndDate;
        this.suspensionPeriodList = suspensionPeriodList;
    }

    public Employee() {
    }

    public Date getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(Date employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public Date getEmploymentEndDate() {
        return employmentEndDate;
    }

    public void setEmploymentEndDate(Date employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public List<SuspensionPeriod> getSuspensionPeriodList() {
        return suspensionPeriodList;
    }

    public void setSuspensionPeriodList(List<SuspensionPeriod> suspensionPeriodList) {
        this.suspensionPeriodList = suspensionPeriodList;
    }
}
