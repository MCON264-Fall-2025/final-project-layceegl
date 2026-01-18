package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Event Planner Mini — see README for instructions.");

        GuestListManager guestListManager = new GuestListManager();
        VenueSelector venueSelector = new VenueSelector(getSampleVenues());
        SeatingPlanner seatingPlanner = null;
        TaskManager taskManager = new TaskManager();
        Venue venue = null;

        loadSampleData(guestListManager);

        int choice;
        do {
            showMenu();
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    loadSampleData(guestListManager);
                    break;
                case 2:
                    addGuest(guestListManager);
                    break;
                case 3:
                    removeGuest(guestListManager);
                    break;
                case 4:
                    venue = selectVenue(venueSelector);
                    break;
                case 5:
                    seatingPlanner = planSeating(guestListManager, venue);
                    break;
                case 6:
                    addTask(taskManager);
                    break;
                case 7:
                    executeTask(taskManager);
                    break;
                case 8:
                    undoTask(taskManager);
                    break;
                case 9:
                    printEventDetails(guestListManager, venue, seatingPlanner, taskManager);
                    break;
                case 0:
                    System.out.println("Event planned successfully.");
                    break;
                default:
                    System.out.println("Please enter a valid choice");
            }
        } while (choice != 0);
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Load Sample Data");
        System.out.println("2. Add Guest");
        System.out.println("3. Remove Guest");
        System.out.println("4. Select Venue");
        System.out.println("5. Plan Seating");
        System.out.println("6. Add Task");
        System.out.println("7. Execute Task");
        System.out.println("8. Undo Task");
        System.out.println("9. Print Event Details");
        System.out.println("0. Exit");
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static void loadSampleData(GuestListManager guestListManager) {
        String[][] sampleGuests = {
                {"Rivka", "Friend"},
                {"Draisy", "Friend"},
                {"Naama", "Kids"},
                {"Yisroel", "Kids"},
                {"Avigdor", "Kids"},
                {"MoZee", "Family"},
                {"Pini", "Family"},
                {"EmJay", "Family"},
                {"Devo", "Family"},
                {"Miriam", "Colleague"},
                {"Avital", "Colleague"},
                {"Dini", "Colleague"},
                {"Toby", "Colleague"},
                {"Bruria", "Friend"},
                {"Shoshana", "Friend"},
                {"Chani", "Friend"}};
        for (String[] guestData : sampleGuests) {
            guestListManager.addGuest(new Guest(guestData[0], guestData[1]));
        }
        System.out.println("Sample data loaded. " + guestListManager.getAllGuests().size() + " guests");
    }

    private static void addGuest(GuestListManager guestListManager) {
        String name = getStringInput("Enter guest name: ");
        String category = getStringInput("Enter guest category (Family, Friend, Colleague, Kids): ");
        guestListManager.addGuest(new Guest(name, category));
        System.out.println("Guest added.");
    }

    private static void removeGuest(GuestListManager guestListManager) {
        String name = getStringInput("Enter guest name to remove: ");
        if (guestListManager.removeGuest(name)) {
            System.out.println("Guest removed.");
        } else {
            System.out.println("Guest not found.");
        }
    }

    private static Venue selectVenue(VenueSelector venueSelector) {
        double budget = getDoubleInput("Enter budget: ");
        int count = getIntInput("Enter number of guests: ");
        Venue venue = venueSelector.selectVenue(budget, count);
        if (venue != null) {
            System.out.println("Venue selected: " + venue.getName() + ", Cost: $" + venue.getCost() + ", Capacity: " + venue.getCapacity());
        } else {
            System.out.println("No suitable venue found.");
        }
        return venue;
    }

    private static SeatingPlanner planSeating(GuestListManager guestListManager, Venue venue) {
        if (venue == null) {
            System.out.println("Please select a venue first.");
            return null;
        }
        SeatingPlanner planner = new SeatingPlanner(venue);
        Map<Integer, List<Guest>> chart = planner.generateSeating(guestListManager.getAllGuests());
        System.out.println("Seating chart (" + chart.size() + " tables):");
        chart.forEach((num, table) ->
                System.out.println(" Table " + num + ": " + table.stream().map(Guest::getName).toList()));
        return planner;
    }

    private static void addTask(TaskManager taskManager) {
        String description = getStringInput("Enter task description: ");
        taskManager.addTask(new Task(description));
        System.out.println("Task added: " + description);
    }

    private static void executeTask(TaskManager taskManager) {
        Task task = taskManager.executeNextTask();
        if (task != null) {
            System.out.println("Executed task: " + task);
        } else {
            System.out.println("No tasks to execute.");
        }
    }

    private static void undoTask(TaskManager taskManager) {
        Task task = taskManager.undoLastTask();
        if (task != null) {
            System.out.println("Undid task: " + task);
        } else {
            System.out.println("No tasks to undo.");
        }
    }

    private static void printEventDetails(GuestListManager guestListManager, Venue venue, SeatingPlanner seatingPlanner, TaskManager taskManager) {
        System.out.println("\nEvent Details: ");
        System.out.println("Guests: " + guestListManager.getAllGuests().size());
        System.out.println("Venue: " + (venue != null ? venue.getName() : "Not selected"));
        System.out.println("Tasks remaining: " + taskManager.remainingTaskCount());
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static double getDoubleInput(String prompt) {
        System.out.println(prompt);
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }

    public static List<Venue> getSampleVenues() {
        return Arrays.asList(
                new Venue("Field", 5000, 200, 20, 10),
                new Venue("Forest", 3000, 150, 15, 10),
                new Venue("Camp", 4000, 180, 18, 10),
                new Venue("Beach", 3500, 160, 16, 10)
        );
    }

    public static List<Guest> getSampleGuests() {
        return Arrays.asList(
                new Guest("Rivka", "Friend"),
                new Guest("Draisy", "Friend"),
                new Guest("Naama", "Kids"),
                new Guest("Yisroel", "Kids"),
                new Guest("Avigdor", "Kids"),
                new Guest("MoZee", "Family"),
                new Guest("Pini", "Family"),
                new Guest("EmJay", "Family"),
                new Guest("Devo", "Family"),
                new Guest("Miriam", "Colleague"),
                new Guest("Avital", "Colleague"),
                new Guest("Dini", "Colleague"),
                new Guest("Toby", "Colleague"),
                new Guest("Bruria", "Friend"),
                new Guest("Shoshana", "Friend"),
                new Guest("Chani", "Friend")
        );
    }
}
