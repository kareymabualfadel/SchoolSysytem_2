package com.kareym.schoolsystem2;

import com.kareym.schoolsystem2.services.SchoolSystem;

/** App entry point. Keeps main() tiny and clean. */
public class Main {
    public static void main(String[] args) {
        SchoolSystem.getInstance().run();
    }
}
