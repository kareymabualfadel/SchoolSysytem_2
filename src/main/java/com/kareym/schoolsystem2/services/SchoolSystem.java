package com.kareym.schoolsystem2.services;

import com.kareym.schoolsystem2.model.Person;
import com.kareym.schoolsystem2.model.Student;
import com.kareym.schoolsystem2.model.Teacher;
import com.kareym.schoolsystem2.util.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
/**
 * Singleton: only one SchoolSystem instance should exist.
 * Why Singleton? It models one application-wide state holder
 * and demonstrates a classic design pattern required by your course.
 */
/**
 * The core service class of the School Management System.
 * <p>
 * It manages a list of students and teachers,
 * provides operations to add, remove, search, and list them,
 * and uses a Singleton pattern to ensure only one instance exists.
 * </p>
 */

public class SchoolSystem{
    private static final Logger log = LoggerFactory.getLogger(SchoolSystem.class);
    private static final String FILE_PATH = "school_data.dat";

    private static SchoolSystem instance;           // Singleton instance
    private final List<Person> people = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    private SchoolSystem() {}

    /**
     * Returns the single SchoolSystem instance (Singleton pattern).
     * @return the global SchoolSystem instance
     */

    public static SchoolSystem getInstance() {
        if (instance == null) {
            instance = new SchoolSystem();
        }
        return instance;
    }

    /**
     * Launches the interactive console menu loop.
     * Handles user input and delegates operations to methods.
     */

    // ===== Menu =====
    public void run() {
        log.info("\n\nStarting SchoolSystem...");
        loadData();   /** here the reading  method implies */

        while (true) {
            System.out.println("\n==== School Management System ====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Teacher");
            System.out.println("3. View All");
            System.out.println("4. Remove by ID");
            System.out.println("5. Exit");
            System.out.println("6. Search by name");
            System.out.println("7. Search by ID");
            System.out.println("8. Show students above a grade");
            System.out.println("9. Back to main menu (Exit)");

            int choice = Input.nextInt(sc, "Choose: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addTeacher();
                case 3 -> showAll();
                case 4 -> removeById();
                case 5 -> {
                    saveData();   /** here the saving method implies */
                    log.info("Exiting... Bye!");
                    return;
                }
                case 6 -> searchByName();
                case 7 -> searchById();
                case 8 -> showStudentsAboveGrade();
                case 9 -> { log.info("Back to main menu..."); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ===== CRUD basics =====
    private void addStudent() {
        String name = Input.nextLine(sc, "Enter name: ");
        int id = Input.nextInt(sc, "Enter ID: ");
        double grade = Input.nextDouble(sc, "Enter grade: ");
        people.add(new Student(id, name, grade));
        log.info("Added Student id={} name={}", id, name);
        System.out.println("✅ Student added.");
    }

    private void addTeacher() {
        String name = Input.nextLine(sc, "Enter name: ");
        int id = Input.nextInt(sc, "Enter ID: ");
        String subject = Input.nextLine(sc, "Enter subject: ");
        people.add(new Teacher(name, id, subject));
        log.info("Added Teacher id={} name={}", id, name);
        System.out.println("✅ Teacher added.");
    }

    private void showAll() {
        if (people.isEmpty()) {
            System.out.println("No records.");
            return;
        }
        // Polymorphic call:
        people.forEach(Person::displayInfo);
    }

    private void removeById() {
        int id = Input.nextInt(sc, "Enter ID to remove: ");
        boolean removed = people.removeIf(p -> p.getId() == id);
        if (removed) {
            log.info("Removed person id={}", id);
            System.out.println("✅ Removed.");
        } else {
            System.out.println("ID not found.");
        }
    }

    /**
     * Searches people whose names contain a given substring.
     * Prints results or informs if none found.
     */
    private void searchByName() {
        String query = Input.nextLine(sc, "Enter name (or part): ").toLowerCase();

        boolean found = false;
        for (Person p : people) {
            if (p.getName().toLowerCase().contains(query)) {
                p.displayInfo();     // polymorphic call
                found = true;
            }
        }

        if (!found)
            System.out.println("No matches for: " + query);

        log.info("Searched by name: {}", query);

    }

    private void searchById() {
        int id = Input.nextInt(sc, "Enter ID to search: ");
        for (Person p : people) {
            if (p.getId() == id) {
                p.displayInfo();
                return;              // stop after first match
            }
        }
        System.out.println("No person with ID " + id);

        log.info("Searched ID: {}", id);

    }

    private void showStudentsAboveGrade() {
        double limit = Input.nextDouble(sc, "Enter minimum grade: ");

        System.out.println("Students with grade ≥ " + limit + ":");
        people.stream()
                .filter(p -> p instanceof Student)                 // keep only students
                .map(p -> (Student) p)                             // cast to Student
                .filter(s -> s.getGrade() >= limit)                // keep by grade
                .sorted((a, b) -> Double.compare(b.getGrade(), a.getGrade())) // descending
                .forEach(Student::displayInfo);                    // print all

        log.info("Listed students above grade {}", limit);

    }



    /**
     * <p>
     *    Saves the current list of people to disk using serialization.
     * <p/>
     *
     * ObjectOutputStream turns objects into binary format.
     *
     * The try(...) block automatically closes the file.
     *
     * people (the list) and all objects inside must be Serializable.
     */
    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(people);
            log.info("Data saved successfully to {}", FILE_PATH);
            System.out.println("✅ Data saved successfully!");
        } catch (IOException e) {
            log.error("Failed to save data", e);
            System.out.println("⚠️ Error saving data: " + e.getMessage());
        }
    }



    /**
     * Loads the list of people from disk if the file exists.
     */
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            log.info("No saved data found.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            List<Person> loaded = (List<Person>) in.readObject();
            people.clear();
            people.addAll(loaded);
            log.info("Data loaded successfully from {}", FILE_PATH);
            System.out.println("✅ Data loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            log.error("Failed to load data", e);
            System.out.println("⚠️ Error loading data: " + e.getMessage());
        }
    }


    // Exposed for tests (next phases)
    public List<Person> getPeople() { return people; }
}
