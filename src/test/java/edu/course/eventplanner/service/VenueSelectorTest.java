package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VenueSelectorTest {
    @Test
    void testSelectVenueBasic() {
        List<Venue> venues = Arrays.asList(
                new Venue("Expensive", 1000, 50, 10, 5),
                new Venue("Cheap", 500, 30, 6, 5)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(600, 25);

        assertEquals("Cheap", selected.getName());
        assertTrue(selected.getCost() <= 600);
        assertTrue(selected.getCapacity() >= 25);
    }

    @Test
    void testSelectVenueNoMatch() {
        List<Venue> venues = Arrays.asList(
                new Venue("Too Expensive", 1000, 50, 10, 5),
                new Venue("Too Small", 400, 20, 5, 4)
        );
        VenueSelector selector = new VenueSelector(venues);

        assertNull(selector.selectVenue(300, 30));
    }

    @Test
    void testSelectVenueCostTie() {
        List<Venue> venues = Arrays.asList(
                new Venue("Big", 500, 100, 20, 5),
                new Venue("Small", 500, 40, 8, 5)  // Same cost, smaller capacity
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(600, 35);
        assertEquals("Small", selected.getName());  // Tiebreaker wins
    }

    @Test
    void testSelectVenueExactBudget() {
        List<Venue> venues = Arrays.asList(
                new Venue("Exact", 500, 50, 10, 5)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(500, 40);
        assertEquals("Exact", selected.getName());
    }

    @Test
    void testSelectVenueZeroGuests() {
        List<Venue> venues = Arrays.asList(
                new Venue("A", 200, 10, 2, 5),
                new Venue("B", 100, 20, 4, 5)
        );
        VenueSelector selector = new VenueSelector(venues);

        Venue selected = selector.selectVenue(300, 0);
        assertEquals("B", selected.getName());  // Lowest cost
    }

    @Test
    void testSelectVenueEmptyList() {
        VenueSelector selector = new VenueSelector(new ArrayList<>());
        assertNull(selector.selectVenue(1000, 10));
    }


}
