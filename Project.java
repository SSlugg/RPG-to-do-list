/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

/**
 *
 * @author Sojeong
 */

import java.util.ArrayList;


public class Project {

    private String title;
    private ArrayList<Task> tasks;

    public Project(String title) {

        this.title = title;
        tasks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addTask(String taskName) {

        tasks.add(new Task(taskName));
    }

    public ArrayList<Task> getTasks() {

        return tasks;
    }


    public void toggleTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).toggle();
        }
    }

    public double getProgress() {
        if (tasks.size() == 0) return 0;

        int completed = 0;

        for (Task t : tasks) {
            if (t.isCompleted()) {
                completed++;
            }
        }

        return (double) completed / tasks.size() * 100;
    }

    public String getProgressBar() {
        double progress = getProgress();
        int filled = (int)(progress / 10);

        String bar = "[";

        for (int i = 0; i < 10; i++) {
            if (i < filled) {
                bar += "█";
            } else {
                bar += "░";
            }
        }

        bar += "]";
        return bar + " " + String.format("%.1f", progress) + "%";
    }

    public void showTasksIndented() {
        if (tasks.isEmpty()) {
            System.out.println("\tNo tasks.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
    }
}
