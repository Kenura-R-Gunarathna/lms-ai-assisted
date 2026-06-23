package com.library;

// a student can borrow and return books
public class Student extends User {

    public Student(String id, String name) {
        super(id, name);
    }

    public String getRole() {
        return "Student";
    }
}
