package com.kareym.schoolsystem2.services;

import com.kareym.schoolsystem2.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SchoolSystemTest {

    @Test
    void addStudentStoresObject() {
        SchoolSystem sys = SchoolSystem.getInstance();
        int before = sys.getPeople().size();
        sys.getPeople().add(new Student(1, "Test", 90.0));
        assertEquals(before + 1, sys.getPeople().size());
    }

    @Test
    void run() {
    }
}
