/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

/**
 *
 * @author Sojeong
 */

import java.util.ArrayList;

public class ProjectManager {
    private ArrayList<Project> projects;

    public ProjectManager() {
        projects = new ArrayList<>();
    }

    public void addProject(String title) {
        projects.add(new Project(title));
    }

    public Project getProject(int index) {
        if (index >= 0 && index < projects.size()) {
            return projects.get(index);
        }
        return null;
    }

    public void showProjects() {
        if (projects.isEmpty()) {
            System.out.println("No projects.");
            return;
        }

        for (int i = 0; i < projects.size(); i++) {
            Project p = projects.get(i);

            System.out.println((i + 1) + ". " + p.getTitle());
            p.showTasksIndented();
            System.out.println();
        }
    }

    public int size() {
        return projects.size();
    }
}
