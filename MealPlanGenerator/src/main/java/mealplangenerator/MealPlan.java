/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplangenerator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;

/**
 *
 * @author jamnn
 */
public class MealPlan {
  MealDatabase mealDatabase;
  boolean[] mealsIncluded;
  int daysOfMeals;
  ArrayList<String> selectedMeals = new ArrayList<>();
  ArrayList<String> selectedMealIngredients;
  ArrayList<String> selectedMealIngredientMeasurements;
  ArrayList<String> selectedMealIngredientAmounts;
  int duplicatePeriod;
  
  public MealPlan(MealDatabase mealDatabase, boolean[] mealOptions, 
          int daysOfMeals, int duplicatePeriod) {
    this.mealDatabase = mealDatabase;
    this.mealsIncluded = mealOptions;
    this.daysOfMeals = daysOfMeals;
    this.duplicatePeriod = duplicatePeriod;
  }
  
  public void generatePlan() {
    if(mealsIncluded[0] || mealsIncluded[1] || mealsIncluded[2]) {
      mealDatabase.buildMealArrays();
      for(int counter = 0; counter < daysOfMeals; counter++) {
        if((counter + 1) % duplicatePeriod == 0)
          mealDatabase.rebuildMealArrays();
        if(mealsIncluded[0]) {
          int randomNumber = ThreadLocalRandom.current().nextInt(0, mealDatabase.breakfastMeals.size());
          selectedMeals.add(mealDatabase.getRandomMeal(1, randomNumber));
        }
        if(mealsIncluded[1]) {
          int randomNumber = ThreadLocalRandom.current().nextInt(0, mealDatabase.lunchMeals.size());
          selectedMeals.add(mealDatabase.getRandomMeal(2, randomNumber));
        }
        if(mealsIncluded[2]) {
          int randomNumber = ThreadLocalRandom.current().nextInt(0, mealDatabase.dinnerMeals.size());
          selectedMeals.add(mealDatabase.getRandomMeal(3, randomNumber));
        }
      }

      int day = 1;
      String displayMealPlan = "Meal Plan:\n";
      int selectedMealCounter = 0;
      while(selectedMealCounter < selectedMeals.size()) {
        displayMealPlan += "\nDay " + day + "\n";
        if(mealsIncluded[0]) {
          displayMealPlan += "Breakfast - " + 
                  mealDatabase.getMealName(selectedMeals.get(selectedMealCounter))
                  + "\n";
          selectedMealCounter++;
        }
        if(mealsIncluded[1]) {
          displayMealPlan += "Lunch - " + 
                  mealDatabase.getMealName(selectedMeals.get(selectedMealCounter))
                  + "\n";
          selectedMealCounter++;
        }
        if(mealsIncluded[2]) {
          displayMealPlan += "Dinner - " + 
                  mealDatabase.getMealName(selectedMeals.get(selectedMealCounter))
                  + "\n";
          selectedMealCounter++;
        }
        day++;
      }

      selectedMealIngredients = mealDatabase.getIngredients(selectedMeals);
      selectedMealIngredientMeasurements = mealDatabase.getIngredientMeasurements();
      selectedMealIngredientAmounts = mealDatabase.getIngredientAmounts();
                  
      ArrayList<String> combinedShoppingList = getShoppingList(selectedMealIngredients);
      
      String displayGroceryList = "";
      for(int counter = 0; counter < combinedShoppingList.size(); counter++) {
        displayGroceryList += combinedShoppingList.get(counter) + "\n";
      }
      JOptionPane.showMessageDialog(null, displayMealPlan, 
                  "Meal Plan", JOptionPane.INFORMATION_MESSAGE);
      JOptionPane.showMessageDialog(null, displayGroceryList, 
                  "Grocery List", JOptionPane.INFORMATION_MESSAGE);
      
      mealDatabase.resetMealPlan();
    }
    else
      JOptionPane.showMessageDialog(null, "You must select at least one meal of the day option!", 
                  "IO Error", JOptionPane.ERROR_MESSAGE);
  }
  
  public ArrayList<String> getShoppingList(ArrayList<String> ingredientList) {
    ArrayList<String> combinedShoppingList = new ArrayList<>();
    ArrayList<String> tempList = new ArrayList<>();
    DecimalFormat format = new DecimalFormat("#,###.###");
    for(int countX = 0; countX < ingredientList.size(); countX++) {
      double totalIngredientCount = Double.parseDouble(selectedMealIngredientAmounts.get(countX));
      for(int countY = countX + 1; countY < ingredientList.size(); countY++) {
        if(ingredientList.get(countX).equals(ingredientList.get(countY))) {
          if(selectedMealIngredientMeasurements.get(countX).equals(selectedMealIngredientMeasurements.get(countY))) {
            totalIngredientCount += Double.parseDouble(selectedMealIngredientAmounts.get(countY));
          }
        }
      }
      if(!tempList.contains(ingredientList.get(countX))) {
        tempList.add(ingredientList.get(countX));
        combinedShoppingList.add(format.format(totalIngredientCount) + " " +
                selectedMealIngredientMeasurements.get(countX) + " " + 
                ingredientList.get(countX));
      }
    }
    return combinedShoppingList;
  }
}
