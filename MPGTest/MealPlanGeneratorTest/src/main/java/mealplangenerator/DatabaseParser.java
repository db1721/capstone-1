package mealplangenerator;

/*
  Meal Plan Generator
  CMSC 495 7381
  Group 5 - Joseph Awonusi, Jordan Bass, Daniel Beck, Zach Burke

  This class parses an input text file to import into a database and parses a database
  to export to a text file.  This allows for easy back-up and sharing of a database
  and gives the user a text version of the database to make edits outside the application.
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public final class DatabaseParser {
  MealDatabase mealDatabase;
  
  // Constructor that handles whether the user is trying to import or export
  public DatabaseParser(MealDatabase mealDatabase, boolean importFlag) {
    this.mealDatabase = mealDatabase;
    if(importFlag) {
      try {
        importDatabase();
      }
      catch(Exception e) {
        JOptionPane.showMessageDialog(null, "Error with database import! Ensure " +
                "input file is formatted correctly!", "Import Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    else
      exportDatabase();
  }
  
  // Asks the user for a directory, exports the database to a text file with the 
  //  date/time prepended for easy identification
  public void exportDatabase() {
    JFileChooser fileChooser = new JFileChooser();
    File workingDirectory = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(workingDirectory);
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    File directoryForExport = null;
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      directoryForExport = fileChooser.getSelectedFile();
    try {
      String fileName = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date())
              + " Database Export.txt";
      Object[] allMeals = mealDatabase.getAllMeals();
      try (FileWriter fileWriter = new FileWriter(new File(directoryForExport.getAbsolutePath()
              + "/" + fileName))) {
        Object[] allMealProperties = mealDatabase.getEditableMealProperties();
        for(Object meal : allMeals) {
          for(Object property : allMealProperties) {
            fileWriter.write(property + ": " + mealDatabase.getMealProperty(meal + "",
                    property + "") + "\n");
          }
          Object[] allIngredientProperties = mealDatabase.getEditableIngredientProperties(meal + "");
          Object[] allMealIngredients = mealDatabase.getMealIngredients(meal + "");
          for(Object ingredient : allMealIngredients) {
            fileWriter.write("INGREDIENT: ");
            for(Object property : allIngredientProperties) {
              fileWriter.write(mealDatabase.getIngredientProperty(meal + "",
                      property + "", ingredient + "") + " ");
            }
            fileWriter.write("\n");
          }
          fileWriter.write("\n");
        }
      }
    } 
    catch (IOException e) {
      System.out.println("An error occurred.");
    }
  }
  
  // Allows the user to select which file they want to import from
  public void importDatabase() {
    JFileChooser fileChooser = new JFileChooser();
    File workingDirectory = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(workingDirectory);
    File fileToImport = null;
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
      fileToImport = fileChooser.getSelectedFile();
    try {
      parseDatabase(fileToImport);
    } 
    catch (Exception ex) {
      Logger.getLogger(DatabaseParser.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  // Parses the text file for import into the existing database
  public void parseDatabase(File fileToParse) throws Exception {
    String name;
    int calories;
    boolean favorite;
    boolean duplicate;
    String mealOfDay;
    double[] ingredientCounts;
    String[] ingredientMeasurements;
    String[] ingredients;
    ArrayList<String> meal = new ArrayList<>();
    
    Scanner fileScanner = new Scanner(fileToParse);
    String tokenToMeal = "";
    while (fileScanner.hasNextLine()) {
      StringTokenizer tokenizer = new StringTokenizer(fileScanner.nextLine());
      while(tokenizer.hasMoreTokens()) {
        String currentToken = tokenizer.nextToken();
        if(currentToken.equalsIgnoreCase("name:")) {
          if(meal.size() > 0) {
            ingredientCounts = new double[(meal.size() - 5)];
            ingredientMeasurements = new String[(meal.size() - 5)];
            ingredients = new String[(meal.size() - 5)];
            name = meal.get(0);
            meal.remove(0);
            calories = Integer.parseInt(meal.get(0));
            meal.remove(0);
            favorite = Boolean.parseBoolean(meal.get(0));
            meal.remove(0);
            duplicate = Boolean.parseBoolean(meal.get(0));
            meal.remove(0);
            mealOfDay = meal.get(0);
            meal.remove(0);
            int count = 0;
            while(meal.size() > 0) {
              StringTokenizer ingredientTokenizer = new StringTokenizer(meal.get(0));
              ingredientCounts[count] = Double.parseDouble(ingredientTokenizer.nextToken());
              ingredientMeasurements[count] = ingredientTokenizer.nextToken();
              String ingredientName = ingredientTokenizer.nextToken() + " ";
              while(ingredientTokenizer.hasMoreTokens()) {
                ingredientName += ingredientTokenizer.nextToken() + " ";
              }
              ingredients[count] = ingredientName;
              meal.remove(0);
              count++;
            }
           mealDatabase.addMeal(name, calories, favorite, duplicate, mealOfDay, 
                   ingredientCounts, ingredientMeasurements, ingredients);
          }
          while(tokenizer.hasMoreTokens()) {
            tokenToMeal += tokenizer.nextToken() + " ";
          }
        }
        
        else if(currentToken.equalsIgnoreCase("calories:") || 
                currentToken.equalsIgnoreCase("favorite:") ||
                currentToken.equalsIgnoreCase("duplicate:") ||
                currentToken.equalsIgnoreCase("meal_of_day:")) {
          tokenToMeal = tokenizer.nextToken();
        }
        
        else if (currentToken.equalsIgnoreCase("ingredient:")){
          while(tokenizer.hasMoreTokens()) {
            tokenToMeal += tokenizer.nextToken() + " ";
          }
        }
        else {
          throw new Exception();
        }
        if(!(tokenToMeal.trim().equals("")))
          meal.add(tokenToMeal.trim());
        tokenToMeal = "";
      }
    }
  }
}
