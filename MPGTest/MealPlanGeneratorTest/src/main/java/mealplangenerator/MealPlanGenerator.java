package mealplangenerator;

/*
  Meal Plan Generator
  CMSC 495 7381
  Group 5 - Joseph Awonusi, Jordan Bass, Daniel Beck, Zach Burke

  This class contains the main method which just launches an iteration of the Meal Plan Generator.
  The Meal Plan Generator launches a GUI that allows a user to interact with a meal database
  in order to create a meal plan and grocery list.
*/

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MealPlanGenerator {
  // Main method
  public static void main(String[] args) {
    new MealPlanGenerator().launchProgam();
  }
  
  // Prompts the user to either select an existing database or create a new one
  public void launchProgam() {
    UIManager.put("Button.defaultButtonFollowsFocus", true);
    if (JOptionPane.showConfirmDialog(null, "Would you like to use an existing database?", 
            "Import Database?", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
      HomeScreen homeScreen = new HomeScreen(JOptionPane.showInputDialog("Enter database name"));
    } 
    else {
      HomeScreen homeScreen = new HomeScreen();
    }
  }
}