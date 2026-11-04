package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class PayrollCalculatorApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the name of the file to process: ");
        String fileName = "src/main/resources/" + input.nextLine();

        System.out.print("Enter the name of the payroll file to create: ");
        String payrollFileName = "src/main/resources/" + input.nextLine();

        try {
            //Open the file with FileReader and wrap it in a BufferedReader
            FileReader fileReader =  new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine(); // throw away header

            FileWriter fileWriter = new FileWriter(payrollFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("id|name|gross pay\n");

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

                String k = String.format("%d|%s|%.2f%n",
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getGrossPay());
                bufferedWriter.write(k);
            }

            bufferedReader.close();
            bufferedWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("file doesn't exist: " + fileName);
        }catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}
