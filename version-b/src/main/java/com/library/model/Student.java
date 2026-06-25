package com.library.model;

/** Version B | Layer: MODEL */
public class Student extends User {

    public Student(String id, String name) {
        super(id, name);
    }

    @Override
    public String getRole() {
        return "Student";
    }
}
