/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplangenerator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author jamnn
 */
public class GeneratePlanScreen extends JFrame{
  String mealName;
  int calorieCount;
  boolean favoriteFlag;
  boolean duplicateFlag;
  String mealOfDay;
  int ingredientSize;
  MealDataRow[] dataRows;
  double[] ingredientCounts;
  String[] ingredientMeasurements;
  String[] ingredients;
  JCheckBox includeBreakfastOption;
  JCheckBox includeLunchOption;
  JCheckBox includeDinnerOption;
  JCheckBox allowDuplicateOption;
  JTextField allowDuplicateInput;
  MealDatabase mealDatabase;
  JFrame frame;
  
  public GeneratePlanScreen(MealDatabase mealDatabase, JFrame frame) {
    this.mealDatabase = mealDatabase;
    this.frame = frame;
    frame.setVisible(false);
    setTitle("Generate A Meal Plan");
    setSize(1024, 768);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    JLabel numberOfMealsLabel = new JLabel("How many days of meals?");
    JTextField numberOfMeals = new JTextField("", 10);
    
    JButton generatePlanButton = new JButton("Generate Plan");
    generatePlanButton.addActionListener((ActionEvent e) -> {
      generatePlan(Integer.parseInt(numberOfMeals.getText()), 
              Integer.parseInt(allowDuplicateInput.getText()));
    });
    
    JButton returnHomeButton = new JButton("Return Home");
    returnHomeButton.addActionListener((ActionEvent e) -> {
      frame.setVisible(true);
      dispose();
    });
    
    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    
    //ButtonGroup mealOfDayButtonGroup = new ButtonGroup();
    
    includeBreakfastOption = new JCheckBox("Breakfast", true);
    includeLunchOption = new JCheckBox("Lunch", true);
    includeDinnerOption = new JCheckBox("Dinner", true);
    allowDuplicateOption = new JCheckBox("Allow Duplicates Every X Days", true);
    allowDuplicateInput = new JTextField("", 3);
    
    panel.add(includeBreakfastOption);
    panel.add(includeLunchOption);
    panel.add(includeDinnerOption);
    panel.add(numberOfMealsLabel);
    panel.add(numberOfMeals);
    panel.add(allowDuplicateOption);
    panel.add(allowDuplicateInput);
    panel.add(generatePlanButton);
    panel.add(returnHomeButton);

    add(panel);
    pack();
    setVisible(true);
  }
  
  public void generatePlan(int mealOptions, int duplicatePeriod) {
    boolean[] mealsIncluded = new boolean[4];
    if(includeBreakfastOption.isSelected())
      mealsIncluded[0] = true;
    if(includeLunchOption.isSelected())
      mealsIncluded[1] = true;
    if(includeDinnerOption.isSelected())
      mealsIncluded[2] = true;
    if(allowDuplicateOption.isSelected())
      mealsIncluded[3] = true;
    
    try {
      MealPlan mealPlan = new MealPlan(mealDatabase, mealsIncluded, 
              mealOptions, duplicatePeriod);
      mealPlan.generatePlan();
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(null, "Incorrect number of meals for parameters! "
              + "Please try again with a larger database, more duplicate meals, or smaller"
              + "duplicate meal window!", 
                "Incorrect Number Of Meals Error", JOptionPane.ERROR_MESSAGE);
    }
    frame.setVisible(true);
    dispose();
  }
}
