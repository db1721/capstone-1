package mealplangenerator;

/*
  Meal Plan Generator
  CMSC 495 7381
  Group 5 - Joseph Awonusi, Jordan Bass, Daniel Beck, Zach Burke

  This class just allows for easily reusable objects for each ingredient in a meal
*/

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class MealDataRow {
  JTextField ingredientCount;
  JComboBox ingredientMeasurement;
  JTextField ingredient;
  
  // Constructor with default options and layout
  public MealDataRow() {
    String measurementOptions[] = {"Count", "Cup", "Oz", "Tbsp", "Tsp", 
      "Lb", "Pint", "Quart", "Gallon", "Pinch"};
    ingredientCount = new JTextField("", 20);
    ingredientMeasurement = new JComboBox(measurementOptions);
    ingredient = new JTextField("", 20);
  }
  
  // Returns the data entered by the user in either the 1st, 2nd, or 3rd box
  public String getData(int dataID) {
    String data = "";
    switch(dataID) {
      case 0:
        return ingredientCount.getText();
      case 1:
        return String.valueOf(ingredientMeasurement.getSelectedItem());
      case 2:
        return ingredient.getText();
    }
    return data;
  }
  
  // Returns the currently selected column
  public JComponent getColumn(int columnNumber) {
    switch(columnNumber) {
      case 0:
        return ingredientCount;
      case 1:
        return ingredientMeasurement;
      case 2:
        return ingredient;
    }
    return null;
  }
  
  // Resets values in each field
  public void reset() {
    ingredientCount.setText("");
    ingredientMeasurement.setSelectedIndex(0);
    ingredient.setText("");
  }
}
