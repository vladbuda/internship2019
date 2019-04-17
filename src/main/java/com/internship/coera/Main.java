package com.internship.coera;

import com.internship.coera.entity.Employee;
import com.internship.coera.entity.Output;
import com.internship.coera.exception.InvalidDateException;
import com.internship.coera.service.EmployeeServiceImplementation;
import com.internship.coera.service.EmployeeService;
import com.internship.coera.validator.EmployeeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Output output = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Stream<String> stringStream = Files.lines(Paths.get("test.json")); //read the file in a stream of strings
            stringStream.forEach(line -> stringBuilder.append(line)); //append each line to a StringBuilder

            //map json to java object
            String employeeJsonString = objectMapper.readTree(stringBuilder.toString()).findValue("employeeData").toString();
            Employee employee = objectMapper.readValue(employeeJsonString, Employee.class);

            //validate employee
            EmployeeValidator.validateEmployee(employee);

            //calculate the output
            EmployeeService service = new EmployeeServiceImplementation();
            output = service.calculateHolidayDays(employee);
        } catch (IOException | InvalidDateException e) {
            output = new Output();
            output.setErrorMessage("invalid file format or date format");
        } finally {
            try {
                objectMapper.writeValue(new File("output.json"), output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
