package com.library.model;

/**
 * Version B - AI-Assisted Approach | Layer: MODEL
 * Abstract user model; concrete roles supply their own name.
 */
public abstract class User {

    protected final String id;
    protected final String name;

    protected User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId()   { return id; }
    public String getName() { return name; }

    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + ": " + name + " (" + id + ")";
    }
}
