package me.benmarten.griddynamics.model;

/**
 * Model to store a row of a specific key-value CSV file.
 */
public class Row {

    private final String key;
    private final String value;

    public Row(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}