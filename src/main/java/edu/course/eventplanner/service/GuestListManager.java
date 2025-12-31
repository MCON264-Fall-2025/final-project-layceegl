package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;

import java.util.*;

public class GuestListManager {
    final LinkedList<Guest> guests = new LinkedList<>();
    final Map<String, Guest> guestByName = new HashMap<>();

    public void addGuest(Guest guest) { /* TODO */
        if (guest == null || guest.getName() == null) return;
        String name = guest.getName();
        if (guestByName.containsKey(name)) {
            return;
        }
        guests.add(guest);
        guestByName.put(name, guest);
    }

    public boolean removeGuest(String guestName) {
        if (guestName == null || !guestByName.containsKey(guestName)) {
            return false;
        }
        Guest guest = guestByName.remove(guestName);
        if (guest == null) {
            return false;
        }
        boolean removedFromList = guests.remove(guest);
        return removedFromList;
    }

    public Guest findGuest(String guestName) {
        if (guestName == null) {
            return null;
        }
        return guestByName.get(guestName);
    }

    public int getGuestCount() {
        return guests.size();
    }

    public List<Guest> getAllGuests() {
        return guests;
    }
}
