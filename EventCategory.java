package com.calendar;

public enum EventCategory {
    LECTURE("Lecture", "#FF6B6B"),          // Red
    ASSIGNMENT("Assignment", "#4ECDC4"),    // Cyan
    MEETING("Meeting", "#FFE66D"),          // Yellow
    PERSONAL("Personal", "#95E1D3");        // Light Green

    private final String displayName;
    private final String color;

    EventCategory(String displayName, String color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColor() {
        return color;
    }
}
