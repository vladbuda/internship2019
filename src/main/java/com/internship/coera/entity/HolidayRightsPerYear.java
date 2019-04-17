package com.internship.coera.entity;

public class HolidayRightsPerYear {
    private int year;
    private int holidayDays;

    public HolidayRightsPerYear(int year, int holidayDays) {
        this.year = year;
        this.holidayDays = holidayDays;
    }

    public HolidayRightsPerYear() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(int holidayDays) {
        this.holidayDays = holidayDays;
    }
}
