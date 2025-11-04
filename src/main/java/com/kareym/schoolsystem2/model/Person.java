package com.kareym.schoolsystem2.model;

import java.io.Serializable;

/**
 * Abstract base class representing a person in the school.
 * <p>
 * It provides common fields and behavior for both Students and Teachers.
 * </p>
 */
public abstract class Person implements Serializable {
    private final int id;
    private String name;

    /**
     * Creates a new Person.
     * @param name the person's name
     * @param id unique identifier
     */
    protected Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /** @return this person's ID */
    public int getId() { return id; }

    /** @return this person's name */
    public String getName() { return name; }

    /** Sets this person's name. */
    public void setName(String name) { this.name = name; }

    /**
     * Displays this person's information on the console.
     * Implemented differently by each subclass.
     */
    public abstract void displayInfo();
}
