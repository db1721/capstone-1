package mealplangenerator;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class MealDataRow {
  JTextField ingredientCount;
  JComboBox ingredientMeasurement;
  JTextField ingredient;
  
  public MealDataRow() {
    String measurementOptions[] = {"", "Count", "Cup", "Oz", "Tbsp", "Tsp", 
      "Lb", "Pint", "Quart", "Gallon", "Pinch"};
    ingredientCount = new JTextField("", 20);
    ingredientMeasurement = new JComboBox(measurementOptions);
    ingredient = new JTextField("", 20);
  }
  
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
  
  public void reset() {
    ingredientCount.setText("");
    ingredientMeasurement.setSelectedIndex(0);
    ingredient.setText("");
  }
}
