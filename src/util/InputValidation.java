package util;

import java.util.Scanner;

/**
 * InputValidation handles and validates different types of user input
 * (byte, int, double, String) from the command line.
 * Prevents program crashes due to invalid input types.
 */
public class InputValidation {
    // Scanner object to read user input from the console.
    private final Scanner scnr = new Scanner(System.in);

    /** Prompts the user until a valid byte value is entered.
     * Uses a loop to handle incorrect input types.
     */
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

    /** Prompts the user until a valid double value is entered.
     * Prevents input mismatch exceptions by checking the input type.
     * @return a validated double value from the user.
     */
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

    /**
     * Prompts the user until a valid integer value is entered.
     * Continues prompting until proper input is received.
     * @return a validated integer value from the user.
     */
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

    /**
     * Reads an entire line of user input as a String.
     * Useful or reading text inputs like VIN numbers or vehicle models.
     * @return the full line of input from the user.
     */
    public String line() {
        String userInput = scnr.nextLine();
        return userInput;
    }

}
