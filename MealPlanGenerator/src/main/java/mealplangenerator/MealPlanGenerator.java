package mealplangenerator;

import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class MealPlanGenerator {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
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