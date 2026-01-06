package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GuestListManagerTest {

    static GuestListManager manager;
    static Guest guest;
    static Guest guest2;

    @BeforeAll
    static void setup() {
        manager = new GuestListManager();
        guest = new Guest("Draisy", "Friends");
        guest2 = new Guest("Rivka", "Friends");
        manager.addGuest(guest);
        manager.addGuest(guest2);
    }

    @Test
    void testAddGuest() {
        Guest newGuest = new Guest("Yael", "Family");
        manager.addGuest(newGuest);
        List<Guest> expected = new LinkedList<>(List.of(guest, guest2, newGuest));
        assertEquals(expected, manager.getAllGuests());
    }

    @Test
    void testRemoveGuest() {
        assertFalse(manager.removeGuest("Rikki"));
    }

    @Test
    void testFindGuest() {
        Guest found = manager.findGuest("Draisy");
        assertEquals("Draisy", found.getName());
        assertEquals("Draisy", manager.findGuest("Draisy").getName());
    }
}
