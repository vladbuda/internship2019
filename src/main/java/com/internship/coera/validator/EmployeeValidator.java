package com.internship.coera.validator;

import com.internship.coera.entity.Employee;
import com.internship.coera.entity.SuspensionPeriod;
import com.internship.coera.exception.InvalidDateException;

import java.sql.Date;
import java.util.List;

public class EmployeeValidator {

    //throws exception if startDate is after endDate
    private static void validateDate(Date startDate, Date endDate) throws InvalidDateException {
        if (startDate.compareTo(endDate) > 0) throw new InvalidDateException("startDate after endDate");
    }

    public static void validateEmployee(Employee employee) throws InvalidDateException {
        Date startDate = employee.getEmploymentStartDate();
        Date endDate = employee.getEmploymentEndDate();
        validateDate(startDate, endDate);
        List<SuspensionPeriod> suspensionPeriodList = employee.getSuspensionPeriodList();
        for (SuspensionPeriod suspensionPeriod : suspensionPeriodList) {
            Date startSuspension = suspensionPeriod.getStartDate();
            Date endSuspension = suspensionPeriod.getEndDate();
            validateDate(startSuspension, endSuspension);
            validateDate(startDate, startDate);
            validateDate(endDate, endDate);
        }
    }
}
