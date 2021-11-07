/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplangenerator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
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
public class EnterMealScreen extends JFrame{
  MealDatabase mealDatabase;
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
  JFrame frame;
  
  public EnterMealScreen(MealDatabase mealDatabase, JFrame frame) {
    this.mealDatabase = mealDatabase;
    this.frame = frame;
    frame.setVisible(false);
    setTitle("Enter A Meal");
    setSize(1024, 768);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    boolean inputAccepted = false;
    while(!inputAccepted) {
      try {
        mealName = JOptionPane.showInputDialog("What is the name of your meal?");
        if(mealName.trim().equals("") || mealName == null)
          throw new Exception();
    
        calorieCount = Integer.parseInt(JOptionPane.showInputDialog("How many calories does "
            + "your meal have per serving?"));
        if(calorieCount < 0)
          throw new Exception();
        
        favoriteFlag = false;
        if(JOptionPane.showConfirmDialog(null, "Is this a favorite meal?", 
                "Favorite?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
          favoriteFlag = true;
        }

        if (JOptionPane.showConfirmDialog(null, "Allow duplicates of this meal in meal plan?", 
                "Duplicates?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
           duplicateFlag = true;
        }

        Object mealsOfDay[] = {"Breakfast", "Lunch", "Dinner"};
        mealOfDay = JOptionPane.showInputDialog(null, "What meal of the day is this?",
            "Meal of Day", JOptionPane.QUESTION_MESSAGE, null, mealsOfDay , "Breakfast") + "";

        ingredientSize = Integer.parseInt(JOptionPane.showInputDialog("How many"
                + " ingredients does your meal have?"));
        if(ingredientSize < 1)
          throw new Exception();
        inputAccepted = true;
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(null, "Incorrect meal parameters! Please try again!", 
                  "IO Error", JOptionPane.ERROR_MESSAGE);
        inputAccepted = false;
      }
    }
    dataRows = new MealDataRow[ingredientSize];
    for(int counter = 0; counter < ingredientSize; counter++) {
      dataRows[counter] = new MealDataRow();
    }
    ingredientCounts = new double[ingredientSize];
    ingredientMeasurements = new String[ingredientSize];
    ingredients = new String[ingredientSize];
    
    JPanel ingredientPanel = new JPanel();
    GridLayout ingredientPanelLayout = new GridLayout(ingredientSize + 2, 3);
    ingredientPanel.setLayout(ingredientPanelLayout);
    ingredientPanel.setBackground(Color.WHITE);
    ingredientPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
    ingredientPanelLayout.setHgap(30);
    ingredientPanelLayout.setVgap(5);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.WHITE);
    
    JLabel ingredientAmountLabel = new JLabel("Ingredient Amount");
    ingredientAmountLabel.setHorizontalAlignment(JLabel.CENTER);
    JLabel ingredientMeasurementLabel = new JLabel("Ingredient Measurement");
    ingredientMeasurementLabel.setHorizontalAlignment(JLabel.CENTER);
    JLabel ingredientLabel = new JLabel("Ingredient Name (Use Singular Form)");
    ingredientLabel.setHorizontalAlignment(JLabel.CENTER);
    
    JTextField ingredientAmountExample = new JTextField("Example: 3");
    ingredientAmountExample.setEditable(false);
    JTextField ingredientMeasurementExample = new JTextField("Example: Count");
    ingredientMeasurementExample.setEditable(false);
    JTextField ingredientExample = new JTextField("Example: Apple");
    ingredientExample.setEditable(false);
    
    ingredientPanel.add(ingredientAmountLabel);
    ingredientPanel.add(ingredientMeasurementLabel);
    ingredientPanel.add(ingredientLabel);
    ingredientPanel.add(ingredientAmountExample);
    ingredientPanel.add(ingredientMeasurementExample);
    ingredientPanel.add(ingredientExample);
    
    for(MealDataRow dataRow : dataRows){
          ingredientPanel.add(dataRow.getColumn(0));
          ingredientPanel.add(dataRow.getColumn(1));
          ingredientPanel.add(dataRow.getColumn(2));
    }
    JButton addMealButton = new JButton("Add Meal");
    addMealButton.addActionListener((ActionEvent e) -> {
      addMeal();
    });
    
    JButton returnHomeButton = new JButton("Return Home");
    returnHomeButton.addActionListener((ActionEvent e) -> {
      frame.setVisible(true);
      dispose();
    });
    
    buttonPanel.add(addMealButton);
    buttonPanel.add(returnHomeButton);

    add(ingredientPanel, "North");
    add(buttonPanel, "South");
    pack();
    setVisible(true);
  }
  
  public void addMeal() {
    int ingredientNumber = 0;
    boolean addSuccess = true;
    while(addSuccess) {
      try {
        for(MealDataRow dataRow : dataRows){
          Double.parseDouble(dataRow.getData(0));      
        }
      }
      catch(NumberFormatException e) {
        addSuccess = false;
        JOptionPane.showMessageDialog(null, "Ingredient amounts must be positive numbers!", 
                  "IO Error", JOptionPane.ERROR_MESSAGE);
      }
      if(addSuccess) {
        for(MealDataRow dataRow : dataRows) {
          ingredientCounts[ingredientNumber] = Double.parseDouble(dataRow.getData(0));
          ingredientMeasurements[ingredientNumber] = dataRow.getData(1);
          ingredients[ingredientNumber] = dataRow.getData(2);
          ingredientNumber++;
          dataRow.reset();
        }
        mealDatabase.addMeal(mealName, calorieCount, favoriteFlag, duplicateFlag,mealOfDay, ingredientCounts, ingredientMeasurements, ingredients);
        JOptionPane.showMessageDialog(null, "Meal successfully entered!", 
                  "Success", JOptionPane.INFORMATION_MESSAGE);
        addSuccess = false;
        frame.setVisible(true);
        dispose();
      }
    }
  }
}
