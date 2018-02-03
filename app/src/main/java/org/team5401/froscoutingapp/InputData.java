package org.team5401.froscoutingapp;

public class InputData {

    private String name;
    private String type;

    public InputData (String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getName () {
        return name;
    }

    public String getType () {
        return type;
    }

    public String toString () {
        String str = type + ":   " + name;
        return str;
    }
}
