package com.kareym.schoolsystem2.model;

public class Student extends Person{
    private double grade;

    public Student(int id, String name, double grade) {
        super(name, id);
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }


    @Override
    public void displayInfo() {
        System.out.println("Student  | ID: " + getId() + " | Name" + getName() + " | Grade: " + getGrade());
    }
}
