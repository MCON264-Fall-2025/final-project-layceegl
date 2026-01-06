package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskManagerTest {
    @Test
    void testAddTask() {
        TaskManager tm = new TaskManager();
        Task task = new Task("Setup chairs");
        tm.addTask(task);
        assertEquals(1, tm.remainingTaskCount());
    }

    @Test
    void testExecuteNextTask() {
        TaskManager tm = new TaskManager();
        Task t1 = new Task("Task1");
        Task t2 = new Task("Task2");
        tm.addTask(t1);
        tm.addTask(t2);
        Task executed = tm.executeNextTask();
        assertEquals(t1, executed);
        assertEquals(1, tm.remainingTaskCount());
    }

    @Test
    void testExecuteNextTaskEmpty() {
        TaskManager tm = new TaskManager();
        assertNull(tm.executeNextTask());
    }

    @Test
    void testUndoLastTask() {
        TaskManager tm = new TaskManager();
        Task task = new Task("Test task");
        tm.addTask(task);
        tm.executeNextTask();
        Task undone = tm.undoLastTask();
        assertEquals(task, undone);
        assertEquals(1, tm.remainingTaskCount());
    }

    @Test
    void testUndoLastTaskEmpty() {
        TaskManager tm = new TaskManager();
        assertNull(tm.undoLastTask());
    }

}
