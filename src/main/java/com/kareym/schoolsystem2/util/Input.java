package com.kareym.schoolsystem2.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/** Centralized safe input helpers: avoids repeated try/catch and newline issues. */
public final class Input {
    private Input() {}

    public static int nextInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = sc.nextInt();
                sc.nextLine(); // consume newline
                return v;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                sc.nextLine();
            }
        }
    }

    public static double nextDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = sc.nextDouble();
                sc.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }
        }
    }

    public static String nextLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
