package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;
import java.util.*;

public class SeatingPlanner {
    private final Venue venue;
    public SeatingPlanner(Venue venue) { this.venue = venue; }
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        Map<String, Queue<Guest>> groups = new HashMap<>();
        for (Guest guest : guests) {
            groups.computeIfAbsent(guest.getGroupTag(), k -> new LinkedList<>()).add(guest);
        }

        Map<Integer, List<Guest>> seating = new TreeMap<>();
        int tableNum = 1;
        int seatsPerTable = venue.getSeatsPerTable();

        for (Queue<Guest> group : groups.values()) {
            while (!group.isEmpty()) {
                List<Guest> currentTable = new ArrayList<>();
                for (int i = 0; i < seatsPerTable && !group.isEmpty(); i++) {
                    currentTable.add(group.poll());
                }
                if (!currentTable.isEmpty()) {
                    seating.put(tableNum++, currentTable);
                }
            }
        }
        return seating;
    }
}
