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
import javax.swing.JComboBox;
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
public final class EditMealOptions extends JFrame{
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
  
  public EditMealOptions(MealDatabase mealDatabase, JFrame frame) {
    this.mealDatabase = mealDatabase;
    this.frame = frame;
    frame.setVisible(false);
    setTitle("Enter A Meal");
    setSize(1024, 768);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    String[] options = {"Meal Properties", "Meal Ingredients", "Cancel"};
    int optionSelected = JOptionPane.showOptionDialog(null, "Would you like to edit meal properties?", 
            "Edit Meals", 0, JOptionPane.QUESTION_MESSAGE, null, options, null);
    switch(optionSelected) {
      case(0):
        editProperties();
        break;
      case(1):
        editIngredients();
        break;
      default:
        frame.setVisible(true);
        dispose();
        break;
    }  
    frame.setVisible(true);
  }
  
  public void editProperties() {
    Object allMeals[] = mealDatabase.getAllMeals();
    String mealToEdit = JOptionPane.showInputDialog(null, "Select a meal to edit.",
        "Select Meal", JOptionPane.QUESTION_MESSAGE, null, allMeals, allMeals[0]) + "";
    
    Object editableProperties[] = mealDatabase.getEditableMealProperties();
    String propertyToEdit = JOptionPane.showInputDialog(null, "Select a property to edit.",
        "Select Property", JOptionPane.QUESTION_MESSAGE, null, editableProperties, 
        editableProperties[0]) + "";
    
    String newPropertyValue = JOptionPane.showInputDialog(null, "What is the new value?",
        "").trim();
    
    mealDatabase.editMealProperty(mealToEdit, propertyToEdit, newPropertyValue);
    String confirmationString = mealToEdit + "'s " + propertyToEdit + " changed to " +
            newPropertyValue + " successfully!";
    JOptionPane.showMessageDialog(null, confirmationString, 
                  "Meal Edit Success!", JOptionPane.INFORMATION_MESSAGE);
  }
  
  public void editIngredients() {
    String[] options = {"Add Ingredient", "Remove Ingredient", "Change Ingredient", 
      "Delete Meal", "Cancel"};
    int optionSelected = JOptionPane.showOptionDialog(null, "Add, remove, or change "
            + "ingredient or delete meal?", "Import Database?", 0, 
            JOptionPane.INFORMATION_MESSAGE, null, options, null);
    
    Object allMeals[] = mealDatabase.getAllMeals();
    String mealToEdit = JOptionPane.showInputDialog(null, "Select a meal to edit.",
        "Select Meal", JOptionPane.QUESTION_MESSAGE, null, allMeals, allMeals[0]) + "";
    
    Object ingredientChoices[] = mealDatabase.getMealIngredients(mealToEdit);
    
    String confirmationString = "";
    switch(optionSelected) {
      case(0):
        String ingredientAmountToAdd = "";
        String ingredientMeasurementToAdd = "";
        String ingredientToAdd = "";
        JPanel customDialogBox = new JPanel();
        String measurementOptions[] = {"", "Count", "Cup", "Oz", "Tbsp", "Tsp", 
        "Lb", "Pint", "Quart", "Gallon", "Pinch"};
        JTextField ingredientCount = new JTextField("Amount of Ingredient", 20);
        JComboBox ingredientMeasurement = new JComboBox(measurementOptions);
        JTextField ingredient = new JTextField("Ingredient Name", 20);
        customDialogBox.add(ingredientCount);
        customDialogBox.add(ingredientMeasurement); // a spacer
        customDialogBox.add(ingredient);
        int result = JOptionPane.showConfirmDialog(null, customDialogBox, 
                 "Add An Ingredient", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
           ingredientAmountToAdd = ingredientCount.getText();
           ingredientMeasurementToAdd = String.valueOf(ingredientMeasurement.getSelectedItem());
           ingredientToAdd = ingredient.getText();
        }
        else
          dispose();
        mealDatabase.addIngredient(mealToEdit, ingredientAmountToAdd, 
                ingredientMeasurementToAdd, ingredientToAdd);
        confirmationString = ingredientToAdd + " added to " + mealToEdit + " successfully!";
        break;
      case(1):
        String ingredientToRemove = JOptionPane.showInputDialog(null, "Select "
                + "an ingredient to remove.", "Select Ingredient", 
                JOptionPane.QUESTION_MESSAGE, null, ingredientChoices, ingredientChoices[0]) + "";
        mealDatabase.removeIngredient(mealToEdit, ingredientToRemove);
        confirmationString = ingredientToRemove + " removed from " + mealToEdit + " successfully!";
        break;
      case(2):
        String ingredientToChange = JOptionPane.showInputDialog(null, "Select "
                + "an ingredient to change.", "Select Ingredient", 
                JOptionPane.QUESTION_MESSAGE, null, ingredientChoices, ingredientChoices[0]) + "";
        Object editableProperties[] = mealDatabase.getEditableIngredientProperties(mealToEdit);
        String propertyToEdit = JOptionPane.showInputDialog(null, "Select a property to edit.",
              "Select Property", JOptionPane.QUESTION_MESSAGE, null, editableProperties, 
              editableProperties[0]) + "";
        String newPropertyValue = JOptionPane.showInputDialog(null, "What is the new value?",
              "").trim();
        mealDatabase.changeIngredient(mealToEdit, ingredientToChange, propertyToEdit, 
                newPropertyValue);
        confirmationString = mealToEdit + "'s " + propertyToEdit + " changed to " +
            newPropertyValue + " successfully!";
        break;
      case(3):
        mealDatabase.deleteMeal(mealToEdit);
        confirmationString = mealToEdit + " deleted successfully!";
      default:
        frame.setVisible(true);
        dispose();
        break;
    }  
    //confirmationString = mealToEdit + "'s " + propertyToEdit + " changed to " +
      //      newPropertyValue + " successfully!";
    JOptionPane.showMessageDialog(null, confirmationString, 
                  "Meal Edit Success!", JOptionPane.INFORMATION_MESSAGE);
  }
}
