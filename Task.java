/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

/**
 *
 * @author Sojeong
 */

public class Task {

    private String name;
    private boolean completed;

    public Task(String name) {

        this.name = name;
        completed = false;
    }

    public boolean isCompleted() {

        return completed;
    }

    public void toggle() {

        completed = !completed;
    }

    public String toString() {

        return (completed ? "[V] " : "[ ] ") + name;
    }
}