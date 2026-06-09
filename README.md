/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

/**
 *
 * @author Sojeong
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;


public class ProductivityRPG extends JFrame {

    // ===== PROJECT DATA =====
    private ArrayList<Project> projects;
    private DefaultListModel<String> projectModel;
    private JList<String> projectList;
    private ArrayList<String> purchasedItems;
    private boolean allItemspurchased = false;

    // ===== TASK DATA =====
    private DefaultListModel<String> taskModel;
    private JList<String> taskList;

    private Project currentProject;

    // ===== USER DATA =====
    private int coins = 0;
    private int exp = 0;
    private int level = 1;

    // ===== STATUS =====
    private JLabel coinLabel;
    private JLabel levelLabel;
    private JLabel expLabel;

    // ===== ROOM =====
    private ImagePanel roomPanel;
    private ImagePanel finalRoom;
    private ArrayList<String> roomItems;

    public ProductivityRPG() {

        projects = new ArrayList<>();
        roomItems = new ArrayList<>();
        purchasedItems = new ArrayList<>();

        setTitle("Productivity RPG");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // ================= LEFT PANEL =================
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(300,600));

        // ===== PROJECT SECTION =====
        JPanel projectPanel = new JPanel(new BorderLayout());

        JLabel projectLabel = new JLabel("PROJECTS");
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);

        projectPanel.add(projectLabel, BorderLayout.NORTH);

        projectModel = new DefaultListModel<>();
        projectList = new JList<>(projectModel);

        JScrollPane projectScroll = new JScrollPane(projectList);

        projectPanel.add(projectScroll, BorderLayout.CENTER);

        JButton createProjectButton = new JButton("Create Project");

        projectPanel.add(createProjectButton, BorderLayout.SOUTH);

        // ===== TASK SECTION =====
        JPanel taskPanel = new JPanel(new BorderLayout());

        JLabel taskLabel = new JLabel("TASKS");
        taskLabel.setHorizontalAlignment(SwingConstants.CENTER);

        taskPanel.add(taskLabel, BorderLayout.NORTH);

        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);

        JScrollPane taskScroll = new JScrollPane(taskList);

        taskPanel.add(taskScroll, BorderLayout.CENTER);

        JPanel taskButtonPanel = new JPanel();

        JButton addTaskButton = new JButton("Add Task");
        JButton completeTaskButton = new JButton("Check / Uncheck");

        taskButtonPanel.add(addTaskButton);
        taskButtonPanel.add(completeTaskButton);

        taskPanel.add(taskButtonPanel, BorderLayout.SOUTH);

        // ===== SPLIT LEFT PANEL =====
        JSplitPane splitPane =
                new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                        projectPanel,
                        taskPanel);

        splitPane.setDividerLocation(250);

        leftPanel.add(splitPane, BorderLayout.CENTER);

        // ================= RIGHT PANEL =================
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JLabel roomTitle = new JLabel("MY DESKTOP");
        roomTitle.setHorizontalAlignment(SwingConstants.CENTER);

        rightPanel.add(roomTitle, BorderLayout.NORTH);

        roomPanel = new ImagePanel("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 14.png");
        roomPanel.setLayout(null);
        roomPanel.setPreferredSize(new Dimension(1401,1123));
        finalRoom = new ImagePanel("C:/Users/Sojeong/Pictures/1/final.png");

        updateRoom();

        rightPanel.add(roomPanel, BorderLayout.CENTER);

        // ===== STATUS =====
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(3, 1));

        coinLabel = new JLabel("Coins: 0");
        levelLabel = new JLabel("Level: 1");
        expLabel = new JLabel("EXP: 0 / 50");

        statusPanel.add(coinLabel);
        statusPanel.add(levelLabel);
        statusPanel.add(expLabel);

        rightPanel.add(statusPanel, BorderLayout.SOUTH);

        // ================= SHOP BUTTON =================
        JButton shopButton = new JButton("Shop");

        rightPanel.add(shopButton, BorderLayout.EAST);

        // ================= MAIN LAYOUT =================
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // =================================================
        // CREATE PROJECT
        // =================================================
        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String projectName = JOptionPane.showInputDialog(
                        "Project Name:"
                );

                if (projectName != null && !projectName.isEmpty()) {

                    Project p = new Project(projectName);

                    projects.add(p);

                    projectModel.addElement(projectName);
                }
            }
        });

        // =================================================
        // SELECT PROJECT
        // =================================================
        projectList.addListSelectionListener(e -> {

            int index = projectList.getSelectedIndex();

            if (index == -1) return;

            currentProject = projects.get(index);

            refreshTaskList();
        });

        // =================================================
        // ADD TASK
        // =================================================
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentProject == null) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Select a project first."
                    );

                    return;
                }

                String task = JOptionPane.showInputDialog(
                        "Task Name:"
                );

                if (task != null && !task.isEmpty()) {

                    currentProject.addTask(task);

                    refreshTaskList();
                }
            }
        });

        // =================================================
        // COMPLETE TASK
        // =================================================
        completeTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentProject == null) return;

                int index = taskList.getSelectedIndex();

                if (index == -1) return;

                Task task = currentProject.getTasks().get(index);

                boolean wasCompleted = task.isCompleted();

                task.toggle();

                if (!wasCompleted && task.isCompleted()) {

                    gainRewards();
                }

                refreshTaskList();
            }
        });

        // =================================================
        // SHOP
        // =================================================
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] items = {
                        "Memo Pad - 10 coin",
                        "Laptop - 10 coin",
                        "Candle - 10 coin",
                        "Pencil Holder - 10 coin",
                        "Clock - 20 coin",
                        "Pencil Case - 20 coin",
                        "Keyboard - 20 coin",
                        "Mug Cup - 20 coin",
                        "Pen Tray - 30 coin",
                        "Books - 50 coin",
                        "Computer Screen - 70 coin",
                        "Plants - 80 coin"
                        
                };

                String choice = (String) JOptionPane.showInputDialog(
                        null,
                        "Choose Item",
                        "Shop",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        items,
                        items[0]
                );

                if (choice == null) return;

                if (choice.contains("Memo Pad")) {
                    buyItem("Memo Pad", 10);
                }

                else if (choice.contains("Laptop")) {
                    buyItem("Laptop", 10);
                }

                else if (choice.contains("Candle")) {
                    buyItem("Candle", 10);
                    
                }

                else if (choice.contains("Pencil Holder")) {
                    buyItem("Pencil Holder", 10);
                    
                }

                else if (choice.contains("Clock")) {
                    buyItem("Clock", 20);
                    
                }

                else if (choice.contains("Pencil Case")) {
                    buyItem("Pencil Case", 20);
                    
                }

                else if (choice.contains("Keyboard")) {
                    buyItem("Keyboard", 20);
                    
                }

                else if (choice.contains("Mug Cup")) {
                    buyItem("Mug Cup", 20);
                    
                }

                else if (choice.contains("Pen Tray")) {
                    buyItem("Pen Tray", 30);
                    
                }

                else if (choice.contains("Books")) {
                    buyItem("Books", 50);
                    
                }

                else if (choice.contains("Computer Screen")) {
                    buyItem("Computer Screen", 70);
                    
                }

                else if (choice.contains("Plants")) {
                    buyItem("Plants", 80);
                    
                }
            
            }
        });

        setVisible(true);
    }

    // =================================================
    // REFRESH TASK LIST
    // =================================================
    public void refreshTaskList() {

        taskModel.clear();

        for (Task t : currentProject.getTasks()) {

            taskModel.addElement(t.toString());
        }
    }

    // =================================================
    // REWARD SYSTEM
    // =================================================
    public void gainRewards() {

        coins += 20;
        exp += 15;

        JOptionPane.showMessageDialog(
                null,
                "+20 Coins\n+15 EXP"
        );

        if (exp >= level * 50) {

            exp = 0;
            level++;

            JOptionPane.showMessageDialog(
                    null,
                    "LEVEL UP!\nLevel " + level
            );
        }

        updateStatus();
    }

    // =================================================
    // STATUS UPDATE
    // =================================================
    public void updateStatus() {

        coinLabel.setText("Coins: " + coins);

        levelLabel.setText("Level: " + level);

        expLabel.setText(
                "EXP: " + exp + " / " + (level * 50)
        );
    }

    // =================================================
    // BUY ITEM
    // =================================================
    public void buyItem(String item, int price) {

        if (allItemspurchased == true) {
            JOptionPane.showMessageDialog(null, "All items already collected!");
            return;
        }

        if (coins < price) {

            JOptionPane.showMessageDialog(
                    null,
                    "Not enough coins!"
            );

            return;
        }

        if (purchasedItems.contains(item)) {
            JOptionPane.showMessageDialog(null, "You already bought this item!");
            return;
        }

        coins -= price;

        roomItems.add(item);
        purchasedItems.add(item);

        updateStatus();

        JOptionPane.showMessageDialog(null, item + " purchased!");

        if (item.equals("Memo Pad")) {
                    
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 6.png",0,0);
                }

        else if (item.equals("Laptop")) {
                
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 7.png",0,0);
        }

        else if (item.equals("Candle")) {
                    
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 3.png",0,0);
        }

        else if (item.equals("Pencil Holder")) {
                    
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 4.png",0,0);
        }

        else if (item.equals("Clock")) {
                   
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 10.png",0,0);
        }

         else if (item.equals("Pencil Case")) {
                
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 5.png",0,0);
        }

        else if (item.equals("Keyboard")) {
                   
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 12.png",0,0);
        }

        else if (item.equals("Mug Cup")) {
                
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 9.png",0,0);
        }

        else if (item.equals("Pen Tray")) {
                   
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 8.png",0,0);
        }

        else if (item.equals("Books")) {
                
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301 11.png",0,0);
        }

        else if (item.equals("Computer Screen")) {
                    
                    addItemToRoom("C:/Users/Sojeong/Downloads/ResizeImage IO/301 2.png",0,0);
        }

        else if (item.equals("Plants")) {
                
                    addItemToRoom("C:/Users/Sojeong/Documents/카카오톡 받은 파일/301.png",0,0);
        }

        checkCompletion();
    }

    public void checkCompletion() {
        if (purchasedItems.contains("Memo Pad")&&purchasedItems.contains("Laptop")&&purchasedItems.contains("Candle")&&
            purchasedItems.contains("Pencil Holder")&&purchasedItems.contains("Clock")&&purchasedItems.contains("Pencil Case")&&
            purchasedItems.contains("Keyboard")&&purchasedItems.contains("Mug Cup")&&purchasedItems.contains("Pen Tray")&&
            purchasedItems.contains("Books")&&purchasedItems.contains("Computer Screen")&&purchasedItems.contains("Plants")) {
                allItemspurchased = true;

                showCompletedRoom();
            }
    }

    public void showCompletedRoom() {
        roomPanel.removeAll();

        finalRoom.setBounds(0, 0, 620, 490);

        roomPanel.add(finalRoom);

        roomPanel.repaint();

        JOptionPane.showMessageDialog(null, "DESKTOP COMPLETE!");
    }

    // =================================================
    // UPDATE ROOM
    // =================================================
    public void updateRoom() {

        roomPanel.repaint();
    }

    // =================================================
    // MAIN
    // =================================================
    public static void main(String[] args) {

        new ProductivityRPG();

    }

    public void addItemToRoom(String imagePath, int x, int y) {

    ImageIcon icon = new ImageIcon(imagePath);

    Image scaled = icon.getImage().getScaledInstance(620, 490, Image.SCALE_SMOOTH);

    JLabel item =
            new JLabel(new ImageIcon(scaled));

    item.setBounds(x, y,620,490);
    
    roomPanel.add(item);
    roomPanel.setComponentZOrder(item, 0);
    roomPanel.revalidate();
    roomPanel.repaint();
    }

    
}


