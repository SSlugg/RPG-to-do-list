/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

/**
 *
 * @author Sojeong
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProjectManager manager = new ProjectManager();

        while (true) {
            System.out.println("\n=== PROJECT MANAGER ===");
            System.out.println("1. Create Project");
            System.out.println("2. View Projects");
            System.out.println("3. Open Project");
            System.out.println("4. Exit");
            System.out.print("Select: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
        		System.out.println("");
                System.out.print("Project name: ");
                String title = sc.nextLine();
                manager.addProject(title);

            } else if (choice == 2) {
        		System.out.println("");
                manager.showProjects();

            } else if (choice == 3) {
        		System.out.println("");
                manager.showProjects();

                if (manager.size() == 0) continue;

                System.out.print("Choose which project to open: ");
                int pIndex = sc.nextInt() - 1;
                sc.nextLine();

                Project project = manager.getProject(pIndex);

                if (project == null) {
                    System.out.println("Invalid project.");
                    continue;
                }

                while (true) {
                    System.out.println("\n=== " + project.getTitle() + " ===");
                    System.out.println("1. Add Task");
                    System.out.println("2. Check / Uncheck Task");
                    System.out.println("3. Show Tasks");
                    System.out.println("4. Back");
                    System.out.print("Select: ");

                    int sub = sc.nextInt();
                    sc.nextLine();

                    if (sub == 1) {
                        System.out.print("Task name: ");
                        String taskName = sc.nextLine();
                        project.addTask(taskName);

                    } else if (sub == 2) {
                        project.showTasksIndented();

                        System.out.print("Task number: ");
                        int t = sc.nextInt();
                        sc.nextLine();

                        project.toggleTask(t - 1);

                    } else if (sub == 3) {
                        project.showTasksIndented();
                        System.out.println(project.getProgressBar());

                    } else if (sub == 4) {
                        break;
                    }
                }

            } else if (choice == 4) {
                break;
            }
        }

        sc.close();
    }
}