package com.discipline.drms.utils.sql;

public class MasterTable {
    private int id;
    private String Type;
    private String Name;
    private String Action;

    // Getters and setters for each field
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }
}
