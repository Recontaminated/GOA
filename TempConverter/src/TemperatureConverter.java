/**************************************************************************
 *  Author: Jeremy Hsieh
 *  Date: 14 July, 2022
 *  Course: GOA CS2: Java
 *
 *  Asks the user for a temperature and then a unit to convert to.
 *
 *************************************************************************/

import java.util.Scanner;

public class TemperatureConverter {
    public static double FahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    public static double CelsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;

    }

    public static void main(String[] args) {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter a temperature: ");
            double temp;
            try {
                temp = in.nextDouble();
            }
            catch (Exception e) {
                System.out.println("Invalid input");
                continue;
            }
            System.out.println("Which unit would you like to convert to? (F or C)");
            String unit = in.next();
            if (unit.equalsIgnoreCase("f")) {
                System.out.println(CelsiusToFahrenheit(temp));
            }
            else if (unit.equalsIgnoreCase("c")) {
                System.out.println(FahrenheitToCelsius(temp));
            }
            else {
                System.out.println("Invalid input");

            }
            System.out.println("Would you like to convert again? (y/anything else to exit)");
            if (!in.next().equalsIgnoreCase("y")) {
                break;
            }

        }

    }
}