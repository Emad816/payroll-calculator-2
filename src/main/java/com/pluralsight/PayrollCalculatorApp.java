package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PayrollCalculatorApp {
    public static void main(String[] args) {

        String fileName = "src/main/resources/employees.csv";

        try {
            //Open the file with FileReader and wrap it in a BufferedReader
            FileReader fileReader =  new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine(); // throw away header

            while ((line = bufferedReader.readLine()) != null) {

                //Split the line using (|)
                String[] lineParts = line.split("\\|");

                //Assigning line parts to variables
                int employeeId = Integer.parseInt(lineParts[0]);
                String name = lineParts[1];
                double hoursWorked = Double.parseDouble(lineParts[2]);
                double payRate = Double.parseDouble(lineParts[3]);

                //Creating new employee object using data above
                Employee employee = new Employee(employeeId, name, hoursWorked, payRate);

                System.out.printf("Id: %d  Name: %s  Gross Pay: $%.2f%n",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getGrossPay());
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("file doesn't exist: " + fileName);
        }catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}
