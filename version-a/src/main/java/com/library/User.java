package com.library;

// abstract user class - the three user types extend this.
// We learned inheritance in class so I used it here.
public abstract class User {

    String id;
    String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // every user type has to tell us its role
    public abstract String getRole();

    public String toString() {
        return getRole() + ": " + name + " (" + id + ")";
    }
}
