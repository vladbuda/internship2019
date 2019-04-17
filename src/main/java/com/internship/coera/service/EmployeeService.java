package com.internship.coera.service;

import com.internship.coera.entity.Employee;
import com.internship.coera.entity.Output;

public interface EmployeeService {
    Output calculateHolidayDays(Employee employee);
}
