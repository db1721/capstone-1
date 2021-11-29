package Database;

/*
  Meal Plan Generator
  CMSC 495 7381
  Group 5 - Joseph Awonusi, Jordan Bass, Daniel Beck, Zach Burke

  This class contains all of the interactions with the Derby database.  This allows
  the user to create a new database or use an existing one, manipulate meal data,
  and there are many getters and setters for meal/ingredient properties.
*/

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.derby.jdbc.EmbeddedDriver;

public class MealDatabase {
	Connection connection;
	ResultSet resultSet;
	String databaseName;
	PreparedStatement preparedStatement;
	Statement statement;
	int mealID;
	int databaseSize[];
	public ArrayList<String> selectedMealIngredientMeasurements;
	public ArrayList<String> selectedMealIngredientAmounts;
	public ArrayList<String> breakfastMeals;
	public ArrayList<String> lunchMeals;
	public ArrayList<String> dinnerMeals;

	// Constructor that creates a new database or loads an existing one
	public MealDatabase(String databaseName, boolean newDatabase) {
		this.databaseName = databaseName;
		selectedMealIngredientMeasurements = new ArrayList<>();
		selectedMealIngredientAmounts = new ArrayList<>();
		// Main meals schema creation
		String createSQL = "create table meals (id integer not null generated always as "
				+ "identity (start with 1, increment by 1), " + "name varchar(60) not null, " + "calories varchar(8), "
				+ "favorite varchar(8)," + "duplicate varchar(9)," + "meal_of_day varchar(11),"
				+ "meal_database varchar(50)," + "constraint primary_key primary key (id))";

		Driver derbyEmbeddedDriver = new EmbeddedDriver();
		try {
			DriverManager.registerDriver(derbyEmbeddedDriver);
			String server = "jdbc:derby:" + databaseName + ";create=true";
			connection = DriverManager.getConnection(server);
			connection.setAutoCommit(true);
			statement = connection.createStatement();
			if (newDatabase)
				statement.execute(createSQL);
			resultSet = statement.executeQuery("select * from meals");
			while (resultSet.next()) {
				mealID++;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database already exists! Using selected database!", "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Allows the user to add a meal to the database with required inputs
	public void addMeal(String name, int calories, boolean favorite, boolean duplicate, String mealOfDay,
			double[] ingredientCounts, String[] ingredientMeasurements, String[] ingredients) {
		name = capitalizeName(name);
		String mealDatabase = name.replaceAll(" ", "") + mealID;

		try {
			preparedStatement = connection.prepareStatement("insert into meals(name,"
					+ "calories,favorite,duplicate,meal_of_day,meal_database) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, calories + "");
			preparedStatement.setString(3, favorite + "");
			preparedStatement.setString(4, duplicate + "");
			preparedStatement.setString(5, mealOfDay);
			preparedStatement.setString(6, mealDatabase);
			preparedStatement.executeUpdate();

			String createSQL = "create table " + mealDatabase + " (id integer not null "
					+ "generated always as identity (start with 1, increment by 1), "
					+ "ingredient_count varchar(16) not null, " + "ingredient_measurement varchar(22), "
					+ "ingredient varchar(50)," + "constraint " + mealDatabase + "_key primary key (id))";
			statement.execute(createSQL);

			for (int ingredientCount = 0; ingredientCount < ingredients.length; ingredientCount++) {
				preparedStatement = connection.prepareStatement("insert into " + mealDatabase
						+ "(ingredient_count,ingredient_measurement,ingredient) values(?,?,?)");
				preparedStatement.setString(1, ingredientCounts[ingredientCount] + "");
				preparedStatement.setString(2, ingredientMeasurements[ingredientCount]);
				preparedStatement.setString(3, capitalizeName(ingredients[ingredientCount] + ""));
				preparedStatement.executeUpdate();
			}
			mealID++;
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Monitors the size of the database in order to display on the home screen
	public int[] getDatabaseCount() {
		databaseSize = new int[4];
		try {
			resultSet = statement.executeQuery("select * from meals where meal_of_day='Breakfast'");
			while (resultSet.next()) {
				databaseSize[0]++;
				databaseSize[3]++;
			}
			resultSet = statement.executeQuery("select * from meals where meal_of_day='Lunch'");
			while (resultSet.next()) {
				databaseSize[1]++;
				databaseSize[3]++;
			}
			resultSet = statement.executeQuery("select * from meals where meal_of_day='Dinner'");
			while (resultSet.next()) {
				databaseSize[2]++;
				databaseSize[3]++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return databaseSize;
	}

	// Returns the currently selected database for display on the home screen
	public String getDatabaseName() {
		return databaseName;
	}

	// Dumps all of the current meals into separate arrays for each meal of the day
	public void buildMealArrays() {
		breakfastMeals = new ArrayList<>();
		lunchMeals = new ArrayList<>();
		dinnerMeals = new ArrayList<>();
		try {
			resultSet = statement.executeQuery("select * from meals");
			while (resultSet.next()) {
				if (resultSet.getString(6).equalsIgnoreCase("breakfast"))
					breakfastMeals.add(resultSet.getString(7));
				else if (resultSet.getString(6).equalsIgnoreCase("lunch"))
					lunchMeals.add(resultSet.getString(7));
				else
					dinnerMeals.add(resultSet.getString(7));
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Resets meals that were used but are allowed to duplicate
	public void rebuildMealArrays() {
		try {
			resultSet = statement.executeQuery("select * from meals");
			while (resultSet.next()) {
				if (resultSet.getString(6).equalsIgnoreCase("breakfast")
						&& resultSet.getString(5).equalsIgnoreCase("true")
						&& !breakfastMeals.contains(resultSet.getString(6))) {
					breakfastMeals.add(resultSet.getString(7));
				} else if (resultSet.getString(6).equalsIgnoreCase("lunch")
						&& resultSet.getString(5).equalsIgnoreCase("true")
						&& !lunchMeals.contains(resultSet.getString(7))) {
					lunchMeals.add(resultSet.getString(7));
				} else if (resultSet.getString(6).equalsIgnoreCase("dinner")
						&& resultSet.getString(5).equalsIgnoreCase("true")
						&& !dinnerMeals.contains(resultSet.getString(7))) {
					dinnerMeals.add(resultSet.getString(7));
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Randomly selects a meal from the appropriate meal of the day
	public String getRandomMeal(int mealOfDay, int randomNumber) {
		String randomMeal;
		switch (mealOfDay) {
		case 1:
			randomMeal = breakfastMeals.get(randomNumber);
			breakfastMeals.remove(randomNumber);
			break;
		case 2:
			randomMeal = lunchMeals.get(randomNumber);
			lunchMeals.remove(randomNumber);
			break;
		default:
			randomMeal = dinnerMeals.get(randomNumber);
			dinnerMeals.remove(randomNumber);
			break;
		}
		return randomMeal;
	}

	// Returns all ingredients of all selected meals
	public ArrayList<String> getIngredients(ArrayList<String> selectedMeals) {
		ArrayList<String> selectedIngredients = new ArrayList<>();
		try {
			for (int counter = 0; counter < selectedMeals.size(); counter++) {
				resultSet = statement.executeQuery("select * from " + selectedMeals.get(counter));
				while (resultSet.next()) {
					selectedMealIngredientAmounts.add(resultSet.getString(2).trim());
					selectedMealIngredientMeasurements.add(resultSet.getString(3).trim());
					selectedIngredients.add(resultSet.getString(4).trim());
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return selectedIngredients;
	}

	// Returns the ingredient quantities
	public ArrayList<String> getIngredientAmounts() {
		return selectedMealIngredientAmounts;
	}

	// Returns the ingredient measurements
	public ArrayList<String> getIngredientMeasurements() {
		return selectedMealIngredientMeasurements;
	}

	// Returns the name of the meal based on its database ID
	public String getMealName(String databaseMealName) {
		String mealName = "";
		try {

			resultSet = statement.executeQuery("select name from meals where meal_database='" + databaseMealName + "'");
			while (resultSet.next()) {
				mealName = resultSet.getString(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return mealName;
	}

	// Commonly used method to format database information
	public String capitalizeName(String nameToCapitalize) {
		String nameToCapitalizeArray[] = nameToCapitalize.split("\\s");
		String capitalizedName = "";
		for (String word : nameToCapitalizeArray) {
			String beginning = word.substring(0, 1);
			String rest = word.substring(1);
			capitalizedName += beginning.toUpperCase() + rest + " ";
		}
		return capitalizedName;
	}

	// Resets all meal plan data
	public void resetMealPlan() {
		selectedMealIngredientMeasurements = new ArrayList<>();
		selectedMealIngredientAmounts = new ArrayList<>();
		buildMealArrays();
	}

	// Returns all meals for menu selection
	public Object[] getAllMeals() {
		Object[] allMeals = new Object[getDatabaseCount()[3]];
		try {
			resultSet = statement.executeQuery("select * from meals");
			int count = 0;
			while (resultSet.next()) {
				allMeals[count] = resultSet.getString(7);
				count++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return allMeals;
	}

	// Returns all meal properties for menu selection
	public Object[] getEditableMealProperties() {
		Object[] allProperties = new Object[5];
		try {
			DatabaseMetaData metadata = connection.getMetaData();
			resultSet = metadata.getColumns(null, null, "MEALS", null);
			int count = 0;
			while (resultSet.next()) {
				if (count > 0 && count < 6)
					allProperties[count - 1] = resultSet.getString("COLUMN_NAME");
				count++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return allProperties;
	}

	// Returns all ingredient properties for menu selection
	public Object[] getEditableIngredientProperties(String mealToEdit) {
		Object[] allProperties = new Object[3];
		try {
			DatabaseMetaData metadata = connection.getMetaData();
			resultSet = metadata.getColumns(null, null, mealToEdit.toUpperCase(), null);
			int count = 0;
			while (resultSet.next()) {
				if (count > 0 && count < 4)
					allProperties[count - 1] = resultSet.getString("COLUMN_NAME");
				count++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return allProperties;
	}

	// Changes a meal property to new value
	public void editMealProperty(String mealToEdit, String propertyToEdit, String newPropertyValue) {
		try {
			statement.execute("update meals set " + propertyToEdit + " = '" + capitalizeName(newPropertyValue)
					+ "' where meal_database = '" + mealToEdit + "'");
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Returns ingredients of a meal for menu selection
	public Object[] getMealIngredients(String mealToRetrieve) {
		Object[] allIngredients = new Object[0];
		try {
			resultSet = statement.executeQuery("select * from " + mealToRetrieve);
			int count = 0;
			while (resultSet.next()) {
				count++;
			}
			allIngredients = new Object[count];
			resultSet = statement.executeQuery("select * from " + mealToRetrieve);
			int countAgain = 0;
			while (resultSet.next()) {
				allIngredients[countAgain] = resultSet.getString(4);
				countAgain++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return allIngredients;
	}

	// Removes an ingredient from the selected meal
	public void removeIngredient(String mealToEdit, String ingredientToRemove) {
		try {
			statement.execute("delete from " + mealToEdit + " where ingredient = '" + ingredientToRemove + "'");
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Adds an ingredient to the selected meal
	public void addIngredient(String mealToEdit, String ingredientAmountToAdd, String ingredientMeasurementToAdd,
			String ingredientToAdd) {
		try {
			preparedStatement = connection.prepareStatement("insert into " + mealToEdit
					+ " (ingredient_count,ingredient_measurement,ingredient) values(?,?,?)");
			preparedStatement.setString(1, ingredientAmountToAdd);
			preparedStatement.setString(2, ingredientMeasurementToAdd);
			preparedStatement.setString(3, capitalizeName(ingredientToAdd).trim());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Changes an ingredient value
	public void changeIngredient(String mealToEdit, String ingredientToChange, String propertyToEdit,
			String newPropertyValue) {
		try {
			statement.execute("update " + mealToEdit + " set " + propertyToEdit + " = '"
					+ capitalizeName(newPropertyValue) + "' where ingredient = '" + ingredientToChange + "'");
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Deletes a meal
	public void deleteMeal(String mealToDelete) {
		try {
			statement.execute("drop table " + mealToDelete);
			statement.execute("delete from meals where meal_database = '" + mealToDelete + "'");

		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// Returns a sepecific property of a specific meal
	public String getMealProperty(String mealToEdit, String propertyToEdit) {
		String propertyValue = "";
		try {
			resultSet = statement.executeQuery(
					"select " + propertyToEdit + " from meals " + "where meal_database = '" + mealToEdit + "'");
			while (resultSet.next()) {
				propertyValue = resultSet.getString(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return propertyValue;
	}

	// Returns a specific property of a specific ingredient of a specific meal
	public String getIngredientProperty(String mealToEdit, String propertyToEdit, String ingredientToEdit) {
		String propertyValue = "";
		try {
			resultSet = statement.executeQuery("select " + propertyToEdit + " from " + mealToEdit
					+ " where ingredient = '" + ingredientToEdit + "'");
			while (resultSet.next()) {
				propertyValue = resultSet.getString(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(MealDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return propertyValue;
	}
}
