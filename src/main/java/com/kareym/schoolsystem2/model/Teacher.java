package com.kareym.schoolsystem2.model;

public class Teacher extends Person {
    private String subject;

    public Teacher(String name, int id, String subject) {
        super(name, id);
        this.subject = subject;
    }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    @Override
    public void displayInfo() {
        System.out.println("Teacher  | ID: " + getId() + " | Name: " + getName() + " | Subject: " + subject);
    }
}
