package mealplangenerator;

/*
  Meal Plan Generator
  CMSC 495 7381
  Group 5 - Joseph Awonusi, Jordan Bass, Daniel Beck, Zach Burke

  This class sets up the GUI which is the main screen for the user to select what
  they want the program to do.
*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.Timer;

public final class HomeScreen extends JFrame {

  MealDatabase mealDatabase;
  JLabel databaseTotalSizeLabel;
  JLabel databaseBreakfastSizeLabel;
  JLabel databaseLunchSizeLabel;
  JLabel databaseDinnerSizeLabel;
  JLabel selectedDatabaseLabel;

  // Constructor that selects an existing database
  public HomeScreen() {
    JFileChooser fileChooser = new JFileChooser();
    File workingDirectory = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(workingDirectory);
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      mealDatabase = new MealDatabase(fileChooser.getSelectedFile().getName() + "", false);
    }
    initGUI();
  }

  // Constructor that creates a new database
  public HomeScreen(String databaseName) {
    mealDatabase = new MealDatabase(databaseName, true);
    initGUI();
  }

  // Sets up the GUI
  public void initGUI() {
    setTitle("Meal Plan Generator");
    setSize(1024, 768);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setIconImage(new ImageIcon(new File(System.getProperty("user.dir")) + "\\classes\\icon.png").getImage());

    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    BufferedImage myPicture = null;
    try {
      myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "\\classes\\logo.png"));
    } catch (IOException ex) {
      Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
    }
    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
    panel.add(picLabel);

    // JLabel titleLabel = new JLabel("Meal Plan & Grocery List Generator");

    // Displays database size
    selectedDatabaseLabel = new JLabel("Selected database: "
            + mealDatabase.getDatabaseName());
    databaseTotalSizeLabel = new JLabel("Total number of meals in database: "
            + mealDatabase.getDatabaseCount()[3]);
    databaseBreakfastSizeLabel = new JLabel("Breakfasts: "
            + mealDatabase.getDatabaseCount()[0]);
    databaseLunchSizeLabel = new JLabel("Lunches: "
            + mealDatabase.getDatabaseCount()[1]);
    databaseDinnerSizeLabel = new JLabel("Dinners: "
            + mealDatabase.getDatabaseCount()[2]);

    // Starts the GUI for generating a meal plan
    JButton generatePlanButton = new JButton("Generate Meal Plan");
    generatePlanButton.addActionListener((ActionEvent e) -> {
      GeneratePlanScreen generatePlanScreen = new GeneratePlanScreen(mealDatabase, this);
    });

    // Essentially restarts the application to clear all database selections
    JButton changeDatabaseButton = new JButton("Change Database");
    changeDatabaseButton.addActionListener((ActionEvent e) -> {
      dispose();
      new MealPlanGenerator().launchProgam();
    });

    // Starts the GUI for entering a meal
    JButton enterMealButton = new JButton("Enter A Meal");
    enterMealButton.addActionListener((ActionEvent e) -> {
      EnterMealScreen enterMealScreen = new EnterMealScreen(mealDatabase, this);
    });

    // Presents the user with options to edit a meal/ingredient
    JButton editMealButton = new JButton("Edit A Meal");
    editMealButton.addActionListener((ActionEvent e) -> {
      EditMealOptions editMealOptions = new EditMealOptions(mealDatabase, this);
    });

    // Allows the user to export the database
    JButton exportDatabaseToTextButton = new JButton("Export Database To Text");
    exportDatabaseToTextButton.addActionListener((ActionEvent e) -> {
      DatabaseParser databaseParser = new DatabaseParser(mealDatabase, false);
    });

    // Allows the user to import a database
    JButton importDatabaseFromTextButton = new JButton("Import Database From Text");
    importDatabaseFromTextButton.addActionListener((ActionEvent e) -> {
      DatabaseParser databaseParser = new DatabaseParser(mealDatabase, true);
    });

    // Terminates the program
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      JOptionPane.showMessageDialog(null, "Thank you for using the Meal Plan Generator!",
              "Quitting Program", JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
    });

    // Screen layout - generated by NetBeans layout manager
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setBackground(Color.WHITE);
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addGap(103, 103, 103)
              .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(generatePlanButton)
                .addGap(27, 27, 27)
                .addComponent(changeDatabaseButton))
              .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(enterMealButton)
              .addComponent(editMealButton))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                  .addComponent(selectedDatabaseLabel)
                  .addComponent(databaseTotalSizeLabel)
                  .addComponent(databaseBreakfastSizeLabel)
                  .addComponent(databaseLunchSizeLabel)
                  .addComponent(databaseDinnerSizeLabel))))
            .addGroup(layout.createSequentialGroup()
              .addGap(98, 98, 98)
              .addComponent(exportDatabaseToTextButton)
              .addGap(18, 18, 18)
              .addComponent(importDatabaseFromTextButton))
            .addGroup(layout.createSequentialGroup()
              .addGap(140, 140, 140)
              .addComponent(quitButton)))
        .addContainerGap(118, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addGap(23, 23, 23)
          .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(generatePlanButton)
            .addComponent(changeDatabaseButton))
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addComponent(enterMealButton)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(editMealButton))
            .addGroup(layout.createSequentialGroup()
              .addComponent(selectedDatabaseLabel)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(databaseTotalSizeLabel)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(databaseBreakfastSizeLabel)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(databaseLunchSizeLabel)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(databaseDinnerSizeLabel)))
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(exportDatabaseToTextButton)
            .addComponent(importDatabaseFromTextButton))
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addComponent(quitButton)
          .addGap(5, 5, 5))
    );

    pack();
    setVisible(true);
    // Timer that constantly displays updates to the database size
    new Timer(500, (ActionEvent ignored) -> {
      databaseTotalSizeLabel.setText("Number of meals in database: " + mealDatabase.getDatabaseCount()[3]);
      databaseBreakfastSizeLabel.setText("Breakfasts: " + mealDatabase.getDatabaseCount()[0]);
      databaseLunchSizeLabel.setText("Lunches: " + mealDatabase.getDatabaseCount()[1]);
      databaseDinnerSizeLabel.setText("Dinners: " + mealDatabase.getDatabaseCount()[2]);
    }).start();
  }
}
