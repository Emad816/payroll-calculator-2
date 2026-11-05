package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class PayrollCalculatorApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Prompt user to enter files names
        System.out.print("Enter the name of the file to process: ");
        String fileName = "src/main/resources/" + input.nextLine();

        System.out.print("Enter the name of the payroll file to create: ");
        String payrollFileName = "src/main/resources/" + input.nextLine();

        boolean isJson = payrollFileName.endsWith(".json");

        try {
            //Open the file with FileReader and wrap it in BufferedReader
            FileReader fileReader =  new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine(); // throw away header

            //Open the file with FileWriter and wrap it in BufferedWriter
            FileWriter fileWriter = new FileWriter(payrollFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if (!isJson) {
                bufferedWriter.write("id|name|gross pay\n");
            }
            if (isJson) {
                bufferedWriter.write("[\n");
            }
            boolean isFirst = true;

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

                if(isJson) {
                    if(!isFirst) {
                        bufferedWriter.write(",\n");
                    }
                    String outputLine = String.format("\t{ \"id\": %d, \"name\" : \"%s\", \"grossPay\" : %.2f }",
                            employee.getEmployeeId(),
                            employee.getName(),
                            employee.getGrossPay());
                    bufferedWriter.write(outputLine);
                    isFirst = false;
                }
                else {
                    String outputLine = String.format("%d|%s|%.2f\n",
                            employee.getEmployeeId(),
                            employee.getName(),
                            employee.getGrossPay());
                    bufferedWriter.write(outputLine);
                }
            }
            if(isJson) {
                bufferedWriter.write("\n]\n");
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
