package com.calendar;

import com.google.gson.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class EventStorageManager {
    private static final String DATA_FILE = "calendar_events.json";
    private final Gson gson;

    public EventStorageManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Save all events to JSON file
     */
    public void saveEvents(Map<LocalDate, List<CalendarApp.Event>> events) {
        try {
            // Convert LocalDate keys to strings for JSON
            Map<String, List<CalendarApp.Event>> eventMap = new HashMap<>();
            for (Map.Entry<LocalDate, List<CalendarApp.Event>> entry : events.entrySet()) {
                eventMap.put(entry.getKey().toString(), entry.getValue());
            }

            String json = gson.toJson(eventMap);
            try (FileWriter writer = new FileWriter(DATA_FILE)) {
                writer.write(json);
            }
            System.out.println("Events saved to " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving events: " + e.getMessage());
        }
    }

    /**
     * Load all events from JSON file
     */
    public Map<LocalDate, List<CalendarApp.Event>> loadEvents() {
        Map<LocalDate, List<CalendarApp.Event>> events = new HashMap<>();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println("No saved events found.");
            return events;
        }

        try {
            String json = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            for (String dateStr : jsonObject.keySet()) {
                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    JsonArray eventArray = jsonObject.getAsJsonArray(dateStr);
                    List<CalendarApp.Event> eventList = new ArrayList<>();

                    for (JsonElement element : eventArray) {
                        JsonObject eventObj = element.getAsJsonObject();
                        String title = eventObj.get("title").getAsString();
                        String time = eventObj.get("time").getAsString();
                        String categoryStr = eventObj.has("category") 
                            ? eventObj.get("category").getAsString() 
                            : "PERSONAL";

                        CalendarApp.Event event = new CalendarApp.Event(title, time);
                        event.setCategory(categoryStr);
                        eventList.add(event);
                    }

                    if (!eventList.isEmpty()) {
                        events.put(date, eventList);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing date " + dateStr + ": " + e.getMessage());
                }
            }

            System.out.println("Events loaded from " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error loading events: " + e.getMessage());
        }

        return events;
    }
}
