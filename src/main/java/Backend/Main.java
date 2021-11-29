package Backend;

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

import GUI.HomeScreenGUI;

import java.awt.EventQueue;

public class Main {
	// Main method
	public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          try {
            // for testing, set to true -- bypasses database selection prompt
            new Main(true);
            //for good luck ;)
            //System.out.println("Hello World");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
	}

	// Prompts the user to either select an existing database or create a new one
	public Main(Boolean testing) {
		if (testing == false) {
			UIManager.put("Button.defaultButtonFollowsFocus", true);
			if (JOptionPane.showConfirmDialog(null, "Would you like to use an existing database?", "Import Database?",
					JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
				new HomeScreenGUI(JOptionPane.showInputDialog("Enter database name"));
			} else {
				new HomeScreenGUI();
			}
		} else {
			new HomeScreenGUI("test-database", false);
		}
	}
}