package com.sg.flooringmastery.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * @date July 2, 2019
 * @author Jack Elder
 * @source Corbin March
 */
public class ConsoleIO {
    private static final String INVALID_NUMBER
            = "[INVALD] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE
            = "[INVALID] Enter a number between %s and %s";
    private static final String REQUIRED
            = "[INVALID] Value is required.";
    private static final String INVALID_DATE
            = "[INVALID] Enter a valid date [mm/dd/yyyy]";

    private final Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.println(message);
    }
    
    public boolean getConfirmation(String prompt) {
        while (true) {
            String verification = readRequiredString(prompt);
            if (verification.equalsIgnoreCase("y")) {
                return true;
            } else if (verification.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            print(REQUIRED);
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                print(INVALID_NUMBER);
            }
        }
    }

    public double readDouble(String prompt, double min, double max) {
        while (true) {
            double result = readDouble(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            if(max == Integer.MAX_VALUE){
                print(String.format("[INVALID] Enter a number greater than %s.", min));
            }else if(min == Integer.MIN_VALUE){
                print(String.format("[INVALID] Enter a number less than %s.", max));
            }else{
                print(String.format(NUMBER_OUT_OF_RANGE, min, max));
            }     
        }
    }
    
    //If the user inputs an empty string, readDoubleOptional returns a double
    // one less than than the input minimum value.
    public double readDoubleOptional(String prompt, double min, double max){
        while(true){
            String stringResult = readString(prompt);
            if(stringResult.isBlank()){
                return (min-1);
            }
            try {
                double result = Double.parseDouble(stringResult);
                if (result >= min && result <= max) {
                    return result;
                }
                if (max == Integer.MAX_VALUE) {
                    print(String.format("[INVALID] Enter a number greater than %s.", min));
                } else if (min == Integer.MIN_VALUE) {
                    print(String.format("[INVALID] Enter a number less than %s.", max));
                } else {
                    print(String.format(NUMBER_OUT_OF_RANGE, min, max));
                }
            } catch (NumberFormatException ex) {
                print(INVALID_NUMBER);
            }
        }
    }

    public float readFloat(String prompt) {
        while (true) {
            try {
                return Float.parseFloat(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                print(INVALID_NUMBER);
            }
        }
    }

    public float readFloat(String prompt, float min, float max) {
        while (true) {
            float result = readFloat(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            print(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public int readInt(String prompt) {
        while(true){
            try{
                return Integer.parseInt(readRequiredString(prompt));
            }catch(NumberFormatException ex){
                print(INVALID_NUMBER);
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while(true){
            int result = readInt(prompt);
            if(result >= min && result <= max){
                return result;
            }
            print(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public long readLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                print(INVALID_NUMBER);
            }
        }
    }

    public long readLong(String prompt, long min, long max) {
        while (true) {
            long result = readLong(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            print(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }
    
    public LocalDate readDate(String prompt) {
        while (true) {
            try {
                String dateString = readRequiredString(prompt);
                if(dateString.length() > 1 && dateString.charAt(1) == '/'){
                    dateString = "0"+dateString;
                }
                return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } catch (DateTimeParseException ex) {
                print(INVALID_DATE);
            }
        }
    }
}
