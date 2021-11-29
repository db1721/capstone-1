package Backend;

/*
  Meal Plan Generator
  CMSC 495 7381
  Group 5 - Joseph Awonusi, Jordan Bass, Daniel Beck, Zach Burke

  This class provides the GUI for the user to select options and then build their
  meal plan and grocery list.
*/

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import Database.MealDatabase;

public class GeneratePlanScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	// Constructor that sets up the GUI and presents the user with options
	public GeneratePlanScreen(MealDatabase mealDatabase, JFrame frame) {
		this.mealDatabase = mealDatabase;
		this.frame = frame;
		frame.setVisible(false);
		setTitle("Generate A Meal Plan");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(new File(System.getProperty("user.dir")) + "\\classes\\icon.png").getImage());

		JLabel numberOfMealsLabel = new JLabel("How many days of meals?");
		JTextField numberOfMeals = new JTextField("", 10);

		// Button to generate meal plan with currently selected options
		JButton generatePlanButton = new JButton("Generate Plan");
		generatePlanButton.addActionListener((ActionEvent e) -> {
			generatePlan(numberOfMeals.getText(), allowDuplicateInput.getText());
		});

		// Returns the user to the home screen
		JButton returnHomeButton = new JButton("Return Home");
		returnHomeButton.addActionListener((ActionEvent e) -> {
			frame.setVisible(true);
			dispose();
		});

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		// Options for meal plan
		includeBreakfastOption = new JCheckBox("Breakfast", true);
        includeBreakfastOption.setBackground(Color.WHITE);
		includeLunchOption = new JCheckBox("Lunch", true);
        includeLunchOption.setBackground(Color.WHITE);
		includeDinnerOption = new JCheckBox("Dinner", true);
        includeDinnerOption.setBackground(Color.WHITE);
        allowDuplicateOption = new JCheckBox("Allow Duplicates Every X Days", false);
        allowDuplicateOption.setBackground(Color.WHITE);
		allowDuplicateInput = new JTextField("", 3);

    GroupLayout layout = new GroupLayout(panel);
    panel.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(67, 67, 67)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(includeDinnerOption)
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(includeBreakfastOption)
              .addComponent(includeLunchOption))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(allowDuplicateOption)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allowDuplicateInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(numberOfMealsLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfMeals, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addGap(53, 53, 53))))
      .addGroup(layout.createSequentialGroup()
        .addGap(103, 103, 103)
        .addComponent(generatePlanButton)
        .addGap(42, 42, 42)
        .addComponent(returnHomeButton)
        .addGap(0, 0, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(45, 45, 45)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent(includeBreakfastOption)
          .addComponent(numberOfMealsLabel)
          .addComponent(numberOfMeals, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent(includeLunchOption)
          .addComponent(allowDuplicateOption)
          .addComponent(allowDuplicateInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(includeDinnerOption)
        .addGap(42, 42, 42)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
          .addComponent(generatePlanButton)
          .addComponent(returnHomeButton))
        .addContainerGap(124, Short.MAX_VALUE))
    );
		add(panel);
		pack();
		setVisible(true);
	}

	// Generates the meal plan
	public void generatePlan(String numberOfMeals, String duplicatePeriod) {
		boolean[] mealOptions = new boolean[4];
		if (includeBreakfastOption.isSelected())
			mealOptions[0] = true;
		if (includeLunchOption.isSelected())
			mealOptions[1] = true;
		if (includeDinnerOption.isSelected())
			mealOptions[2] = true;
		int numberMeals = 0;
		int duplicateDuration = 0;

		// Ensures the user entered positive integers
		try {
			numberMeals = Integer.parseInt(numberOfMeals);
			if (allowDuplicateOption.isSelected()) {
				mealOptions[3] = true;
				duplicateDuration = Integer.parseInt(duplicatePeriod);
        if(duplicateDuration < 1)
          throw new Exception();
			}
      if(numberMeals < 1)
				throw new Exception();
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(null, "Duplicate period and number of days "
              + "must be integers greater than 0", 
					"Incorrect Number Format", JOptionPane.ERROR_MESSAGE);
		}

    // Lets the user know if the meal plan doesn't support the current options selected
		try {
      MealPlan mealPlan = new MealPlan(mealDatabase, mealOptions, 
              numberMeals, duplicateDuration);
			mealPlan.generatePlan();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Incorrect number of meals for parameters! "
							+ "Please try again with a larger database, more duplicate meals, or smaller"
							+ " duplicate meal window!",
					"Incorrect Number Of Meals Error", JOptionPane.ERROR_MESSAGE);
		}
		frame.setVisible(true);
		dispose();
	}
}
