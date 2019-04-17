package com.internship.coera.service;

import com.internship.coera.entity.Employee;
import com.internship.coera.entity.HolidayRightsPerYear;
import com.internship.coera.entity.Output;
import com.internship.coera.entity.SuspensionPeriod;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeServiceImplementation implements EmployeeService {

    @Override
    public Output calculateHolidayDays(Employee employee){
        Output output = new Output();
        LocalDate startDate = LocalDate.parse(employee.getEmploymentStartDate().toString());
        LocalDate endDate = LocalDate.parse(employee.getEmploymentEndDate().toString());
        List<SuspensionPeriod> suspensionPeriodList = employee.getSuspensionPeriodList();

        Map<Integer, Integer> holidayDaysPerYear = new HashMap<>();

        double noOfHolidayDays = 20.0;
        double daysWeight = 0.0; //how much each day contributes to the number of holiday days
        long daysSuspended = ChronoUnit.DAYS.between(LocalDate.of(startDate.getYear(),1,1), startDate); //initialize with the number of days not worked in the first year (0 if the employee starts in yyyy-01-01)

        for(int year = startDate.getYear(); year <= endDate.getYear(); year++){
            LocalDate startOfTheCurrentYear = LocalDate.of(year,1,1); //the first date of the current year
            LocalDate endOfTheCurrentYear = LocalDate.of(year,12,31); //the last date of the current year
            for(SuspensionPeriod suspensionPeriod : suspensionPeriodList){
                LocalDate suspensionStartDate = LocalDate.parse(suspensionPeriod.getStartDate().toString());
                LocalDate suspensionEndDate = LocalDate.parse(suspensionPeriod.getEndDate().toString());

                if(suspensionStartDate.compareTo(endOfTheCurrentYear) < 0) { //check if the current suspension begins in the current year
                    if (suspensionEndDate.compareTo(endOfTheCurrentYear) < 0) { //check if the current suspension ends throughout the current year
                        daysSuspended += ChronoUnit.DAYS.between(suspensionStartDate, suspensionEndDate); //calculate the number of suspended days
                    }
                    else{ //the current suspension starts in the current year but it ends in the next one
                        daysSuspended += ChronoUnit.DAYS.between(suspensionStartDate, endOfTheCurrentYear); //find the number of suspended days between the suspension start date and the end of the current year
                        Date date = Date.from(startOfTheCurrentYear.plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                        suspensionPeriod.setStartDate(new java.sql.Date(date.getTime())); //modify the start date of the current suspension to 01-01 of the next year
                    }
                }
            }
            if(year == endDate.getYear()){//for the last year, get the number of days between the end of the contract and the end of the year
                daysSuspended += ChronoUnit.DAYS.between(endDate, endOfTheCurrentYear);
            }
            if(startOfTheCurrentYear.isLeapYear()) {//check if the current year is a leap year and calculate accordingly
                daysWeight = noOfHolidayDays / 366;
                holidayDaysPerYear.put(year, (int)Math.round(daysWeight*(366-daysSuspended))); //calculates the number of holiday days for the current year based on the suspended days and the "weight of each day" (the result is rounded based on the mathematical rules: 9.4 => 9 and 9.6 => 10)
            }
            else {
                daysWeight = noOfHolidayDays / 365;
                holidayDaysPerYear.put(year, (int)Math.round(daysWeight*(365-daysSuspended)));
            }
            if(noOfHolidayDays < 24) noOfHolidayDays += 1; //increment the number of holiday days the employee can have
            daysSuspended = 0;
        }
        List<HolidayRightsPerYear> holidayRightsPerYearList = holidayDaysPerYear.keySet().stream().map(x->new HolidayRightsPerYear(x, holidayDaysPerYear.get(x))).collect(Collectors.toList()); //construct the list of HolidayRightPerYear object from the previously calculated map
        output.setHolidayRightPerYearList(holidayRightsPerYearList);
        return output;
    }
}
