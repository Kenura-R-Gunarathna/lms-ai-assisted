package com.library;

// admin - manages accounts and reports
public class Administrator extends User {

    public Administrator(String id, String name) {
        super(id, name);
    }

    public String getRole() {
        return "Administrator";
    }
}
