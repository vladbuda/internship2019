package com.internship.coera.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Output {
    private String errorMessage;
    private List<HolidayRightsPerYear> holidayRightPerYearList;

    public Output(String message, List<HolidayRightsPerYear> holidayRightPerYearList) {
        this.errorMessage = message;
        this.holidayRightPerYearList = holidayRightPerYearList;
    }

    public Output() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<HolidayRightsPerYear> getHolidayRightPerYearList() {
        return holidayRightPerYearList;
    }

    public void setHolidayRightPerYearList(List<HolidayRightsPerYear> holidayRightPerYearList) {
        this.holidayRightPerYearList = holidayRightPerYearList;
    }
}
