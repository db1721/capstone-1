package mealplangenerator;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public final class HomeScreen extends JFrame {
  MealDatabase mealDatabase;
  JLabel databaseTotalSizeLabel;
  JLabel databaseBreakfastSizeLabel;
  JLabel databaseLunchSizeLabel;
  JLabel databaseDinnerSizeLabel;
  JLabel selectedDatabaseLabel;
  
  public HomeScreen() {
    JFileChooser fileChooser = new JFileChooser();
    File workingDirectory = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(workingDirectory);
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
      mealDatabase = new MealDatabase(fileChooser.getSelectedFile().getName() + "", false);
    initGUI();
  }
  
  public HomeScreen(String databaseName) {
    mealDatabase = new MealDatabase(databaseName, true);
    initGUI();
  }
  
  public void initGUI() {
    setTitle("Meal Plan Generator");
    setSize(1024, 768);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
        
    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    
    JLabel titleLabel = new JLabel("Meal Plan & Grocery List Generator");
    
    selectedDatabaseLabel = new JLabel("Selected database: " +
            mealDatabase.getDatabaseName());
    
    databaseTotalSizeLabel = new JLabel("Total number of meals in database: " + 
            mealDatabase.getDatabaseCount()[3]);
    databaseBreakfastSizeLabel = new JLabel("Breakfasts: " + 
            mealDatabase.getDatabaseCount()[0]);
    databaseLunchSizeLabel = new JLabel("Lunches: " + 
            mealDatabase.getDatabaseCount()[1]);
    databaseDinnerSizeLabel = new JLabel("Dinners: " + 
            mealDatabase.getDatabaseCount()[2]);
    
    JButton generatePlanButton = new JButton("Generate Meal Plan");
    generatePlanButton.addActionListener((ActionEvent e) -> {
      new GeneratePlanScreen(mealDatabase, this);
    });
    
    /*JButton importDatabaseButton = new JButton("Import Meals");
    importDatabaseButton.addActionListener((ActionEvent e) -> {
      importMeals(mealDatabase);
    });*/
       
    JButton enterMealButton = new JButton("Enter A Meal");
    enterMealButton.addActionListener((ActionEvent e) -> {
      new EnterMealScreen(mealDatabase, this);
    });
            
    JButton editMealButton = new JButton("Edit A Meal");
    editMealButton.addActionListener((ActionEvent e) -> {
      new EditMealOptions(mealDatabase, this);
    });
            
    JButton clearDatabaseButton = new JButton("Clear Database");
    clearDatabaseButton.addActionListener((ActionEvent e) -> {
      //clear database
    });
    
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      JOptionPane.showMessageDialog(null, "Thank you for using the Meal Plan Generator!", 
                  "Quitting Program", JOptionPane.INFORMATION_MESSAGE);
      System.exit(0);
    });
        
    panel.add(titleLabel);
    panel.add(generatePlanButton);
    //panel.add(importDatabaseButton);
    panel.add(enterMealButton);
    panel.add(editMealButton);
    //panel.add(clearDatabaseButton);
    panel.add(selectedDatabaseLabel);
    panel.add(databaseTotalSizeLabel);
    panel.add(databaseBreakfastSizeLabel);
    panel.add(databaseLunchSizeLabel);
    panel.add(databaseDinnerSizeLabel);
    panel.add(quitButton);
    
    add(panel);
    setVisible(true);
    new Timer(100, (ActionEvent ignored) -> {
      databaseTotalSizeLabel.setText("Number of meals in database: " + mealDatabase.getDatabaseCount()[3]);
      databaseBreakfastSizeLabel.setText("Breakfasts: " + mealDatabase.getDatabaseCount()[0]);
      databaseLunchSizeLabel.setText("Lunches: " + mealDatabase.getDatabaseCount()[1]);
      databaseDinnerSizeLabel.setText("Dinners: " + mealDatabase.getDatabaseCount()[2]);
    }).start();
  }
}
