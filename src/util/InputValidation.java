package util;

import java.util.Scanner;

public class InputValidation {
    private final Scanner scnr = new Scanner(System.in);
    public byte validateByte() {
        byte value = 0;
        boolean valid = false;
        while (!valid) {
            if (scnr.hasNextByte()) {
                value = scnr.nextByte();
                scnr.nextLine();
                valid = true;
            }
            else {
                System.out.printf("Invalid input, %s, please enter a valid numerical value: ", scnr.nextLine());
            }
        }

        return value;
    }

    public double validateDouble() {
        double value = 0;
        boolean valid = false;
        while (!valid) {
            if (scnr.hasNextDouble()) {
                value = scnr.nextDouble();
                scnr.nextLine();
                valid = true;
            }
            else {
                System.out.printf("Invalid input, %s, please enter a valid numerical value: ", scnr.nextLine());
            }
        }

        return value;
    }

    public int validateInt() {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            if (scnr.hasNextInt()) {
                value = scnr.nextInt();
                scnr.nextLine();
                valid = true;
            }
            else {
                System.out.printf("Invalid input, %s, please enter a valid numerical value: ", scnr.nextLine());
            }
        }

        return value;
    }

    public String line() {
        String userInput = scnr.nextLine();
        return userInput;
    }

}
