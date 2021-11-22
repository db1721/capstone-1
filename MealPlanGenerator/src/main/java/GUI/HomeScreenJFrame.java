package GUI;

import java.awt.BorderLayout;
//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
//import javax.swing.border.EmptyBorder;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Backend.EditMealOptions;
import Backend.EnterMealScreen;
import Backend.GeneratePlanScreen;
import Backend.Main;
import Database.MealDatabase;

public class HomeScreenJFrame extends JFrame {
	/***************************************************************
	 * Global
	 ****************************************************************/
	private static final long serialVersionUID = 1L;
	MealDatabase mealDatabase;

	// Constructor that selects an existing database
	public HomeScreenJFrame() {
		JFileChooser fileChooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(workingDirectory);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			mealDatabase = new MealDatabase(fileChooser.getSelectedFile().getName() + "", false);
		}
		initGUI();
	}

	// Constructor that creates a new database
	public HomeScreenJFrame(String databaseName) {
		mealDatabase = new MealDatabase(databaseName, true);
		initGUI();
	}

	// Constructor for testing
	public HomeScreenJFrame(String databaseName, Boolean notNew) {
		mealDatabase = new MealDatabase(databaseName, notNew);
		initGUI();
	}

	/**
	 * Create the frame.
	 * 
	 * @return
	 */
	public void initGUI() {
		/***************************************************************
		 * GUI basics
		 ****************************************************************/
		setBounds(100, 100, 800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Meal Plan Generator");
		setResizable(false);
		setVisible(true);
		getContentPane().setBackground(Color.WHITE);
		setIconImage(new ImageIcon(new File(System.getProperty("user.dir")) + "\\classes\\icon.png").getImage());
		getContentPane().setLayout(null);

		/***************************************************************
		 * Left Panel - Home
		 ****************************************************************/
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 392, 471);
		leftPanel.setBackground(Color.WHITE);
		getContentPane().add(leftPanel);
		leftPanel.setLayout(new BorderLayout(0, 0));

		/***************************************************************
		 * Left Panel - Logo
		 ****************************************************************/
		// For testing
//		JLabel picLabel = new JLabel(new ImageIcon("C:\\Users\\danbe\\OneDrive\\Desktop\\GitHub\\Capstone-Colab\\MealPlanGenerator\\src\\main\\resources\\logo.png"));
		JLabel picLabel = new JLabel(new ImageIcon(System.getProperty("user.dir") + "\\classes\\logo.png"));
		leftPanel.add(picLabel);

		/***************************************************************
		 * Right Panel - Home
		 ****************************************************************/
		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(396, 0, 400, 471);
		rightPanel.setBackground(SystemColor.activeCaption);
		getContentPane().add(rightPanel);
		rightPanel.setLayout(null);

		/***************************************************************
		 * Right Panel - Generate Meal Plan Button
		 ****************************************************************/
		JButton btnGenerateMeal = new JButton("Generate Meal Plan");
		btnGenerateMeal.setBounds(105, 103, 190, 23);
		rightPanel.add(btnGenerateMeal);
		btnGenerateMeal.addActionListener(event -> {
			new GeneratePlanScreen(mealDatabase, this);
		});

		/***************************************************************
		 * Right Panel - Change Database Button
		 ****************************************************************/
		JButton btnChangeDB = new JButton("Change Database");
		btnChangeDB.setBounds(105, 137, 190, 23);
		rightPanel.add(btnChangeDB);
		btnChangeDB.addActionListener(event -> {
			dispose();
			new Main(true);
		});

		/***************************************************************
		 * Right Panel - Enter A Meal Button
		 ****************************************************************/
		JButton btnEnterMeal = new JButton("Enter a Meal");
		btnEnterMeal.setBounds(105, 171, 190, 23);
		rightPanel.add(btnEnterMeal);
		btnEnterMeal.addActionListener(event -> {
			new EnterMealScreen(mealDatabase, this);
//			rightPanel.setVisible(false);
		});

		/***************************************************************
		 * Right Panel - Edit A Meal Button
		 ****************************************************************/
		JButton btnEditMeal = new JButton("Edit a Meal");
		btnEditMeal.setBounds(105, 205, 190, 23);
		rightPanel.add(btnEditMeal);
		btnEditMeal.addActionListener(event -> {
			new EditMealOptions(mealDatabase, this);
		});

		/***************************************************************
		 * Right Panel - Export Database to Text Button
		 ****************************************************************/
		JButton btnExport = new JButton("Export Database to Text");
		btnExport.setBounds(105, 239, 190, 23);
		rightPanel.add(btnExport);
		btnExport.addActionListener(event -> {
			new Database.DatabaseParser(mealDatabase, false);
		});

		/***************************************************************
		 * Right Panel - Import Database from Text Button
		 ****************************************************************/
		JButton btnImport = new JButton("Import Database from Text");
		btnImport.setBounds(105, 273, 190, 23);
		rightPanel.add(btnImport);
		btnImport.addActionListener(event -> {
			new Database.DatabaseParser(mealDatabase, true);
		});

		/***************************************************************
		 * Right Panel - Quit Button
		 ****************************************************************/
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(105, 307, 190, 23);
		rightPanel.add(btnQuit);
		btnQuit.addActionListener(event -> {
			JOptionPane.showMessageDialog(null, "Thank you for using the Meal Plan Generator!", "Quitting Program",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		});

		/***************************************************************
		 * Right Panel - Labels to display database size
		 ****************************************************************/
		JLabel lblSelectOption = new JLabel("Select an Option\r\n");
		lblSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectOption.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 28));
		lblSelectOption.setBounds(10, 27, 380, 38);
		rightPanel.add(lblSelectOption);

		JLabel lblMealsInDB = new JLabel("Number of meals in database: " + mealDatabase.getDatabaseCount()[3]);
		lblMealsInDB.setBounds(10, 396, 200, 14);
		rightPanel.add(lblMealsInDB);

		JLabel lblSelectedDB = new JLabel("Selected Database: " + mealDatabase.getDatabaseName());
		lblSelectedDB.setBounds(10, 371, 200, 14);		
		rightPanel.add(lblSelectedDB);

		JLabel lblBreakfasts = new JLabel("Breakfasts: " + mealDatabase.getDatabaseCount()[0]);
		lblBreakfasts.setBounds(220, 371, 170, 14);
		rightPanel.add(lblBreakfasts);

		JLabel lblLunches = new JLabel("Lunches: " + mealDatabase.getDatabaseCount()[1]);
		lblLunches.setBounds(220, 396, 170, 14);
		rightPanel.add(lblLunches);

		JLabel lblDinners = new JLabel("Dinners: " + mealDatabase.getDatabaseCount()[2]);
		lblDinners.setBounds(220, 421, 170, 14);
		rightPanel.add(lblDinners);

		// Timer that constantly displays updates to the database size
		new Timer(500, (ActionEvent ignored) -> {
			lblMealsInDB.setText("Number of meals in database: " + mealDatabase.getDatabaseCount()[3]);
			lblBreakfasts.setText("Breakfasts: " + mealDatabase.getDatabaseCount()[0]);
			lblLunches.setText("Lunches: " + mealDatabase.getDatabaseCount()[1]);
			lblDinners.setText("Dinners: " + mealDatabase.getDatabaseCount()[2]);
		}).start();
	}
}