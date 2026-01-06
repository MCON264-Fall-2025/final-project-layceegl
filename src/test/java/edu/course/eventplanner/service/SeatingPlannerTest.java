package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeatingPlannerTest {
    @Test
    void testGenerateSeatingBasic() {
        Venue venue = new Venue("Small", 500, 20, 5, 4);
        SeatingPlanner planner = new SeatingPlanner(venue);
        List<Guest> guests = Arrays.asList(
                new Guest("Draisy", "friends"),
                new Guest("Rivka", "friends")
        );
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);
        assertEquals(1, seating.size());
        assertEquals(2, seating.get(1).size());
    }

    @Test
    void testSeatingByGroup() {
        Venue venue = new Venue("Hall", 1000, 24, 6, 4);
        SeatingPlanner planner = new SeatingPlanner(venue);
        List<Guest> guests = Arrays.asList(
                new Guest("EmJay", "family"), new Guest("MoZee", "family"),
                new Guest("Draisy", "friends"), new Guest("Rivka", "friends")
        );
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);
        assertEquals(2, seating.size());
        assertTrue(seating.get(1).stream().allMatch(g -> "family".equals(g.getGroupTag())));
    }

    @Test
    void testLargeGroupSpansTables() {
        Venue venue = new Venue("Large", 2000, 40, 10, 4);
        SeatingPlanner planner = new SeatingPlanner(venue);
        List<Guest> family = Arrays.asList(
                new Guest("EmJay", "family"), new Guest("MoZee", "family"),
                new Guest("Pini", "family"), new Guest("Yael", "family"),
                new Guest("Devo", "family"), new Guest("Yehuda", "family")
        );
        Map<Integer, List<Guest>> seating = planner.generateSeating(family);
        assertTrue(seating.size() >= 2);
        seating.values().forEach(t -> assertTrue(t.stream().allMatch(g -> "family".equals(g.getGroupTag()))));
    }

    @Test
    void testTablesOrdered() {
        Venue venue = new Venue("Ordered", 800, 16, 4, 4);
        SeatingPlanner planner = new SeatingPlanner(venue);
        List<Guest> guests = Arrays.asList(new Guest("Michoel", "Kids"), new Guest("Yisroel", "Kids"));
        Map<Integer, List<Guest>> seating = planner.generateSeating(guests);
        assertEquals(1, seating.keySet().iterator().next().intValue());
    }

    @Test
    void testEmptyGuests() {
        Venue venue = new Venue("Empty", 500, 20, 5, 4);
        SeatingPlanner planner = new SeatingPlanner(venue);
        Map<Integer, List<Guest>> seating = planner.generateSeating(new ArrayList<>());
        assertTrue(seating.isEmpty());
    }

}
