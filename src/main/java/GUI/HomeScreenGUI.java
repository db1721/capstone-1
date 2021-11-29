package GUI;

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
import Backend.Main;
import Database.MealDatabase;
import Utilities.RandomNumber;

public class HomeScreenGUI extends JFrame {
	/***************************************************************
	 * Global
	 ****************************************************************/
	private static final long serialVersionUID = 1L;
	MealDatabase mealDatabase;
//	JPanel leftPanelMain;
//	JPanel rightPanelMain;

	// Constructor that selects an existing database
	public HomeScreenGUI() {
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
	public HomeScreenGUI(String databaseName) {
		mealDatabase = new MealDatabase(databaseName, true);
		initGUI();
	}

	// Constructor for testing
	public HomeScreenGUI(String databaseName, Boolean notNew) {
		mealDatabase = new MealDatabase(databaseName, notNew);
		initGUI();
	}

	/**
	 * Creates the frame.
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
		 * Left Panel - Logo
		 ****************************************************************/
		JPanel leftPanelMain = new JPanel();
		leftPanelMain.setBounds(0, 0, 392, 471);
		leftPanelMain.setBackground(Color.WHITE);
		getContentPane().add(leftPanelMain);
        
		// Dan playing around with random logo selection
		RandomNumber logoPicker = new RandomNumber(3); // how many logos are in the classes folder?
		int logoPicked = logoPicker.getNewNum();
		leftPanelMain.setLayout(null);
		JLabel picLabel = new JLabel(
				// add .getNewNum here when figured out. Also add or remove the "%d" after logo
				new ImageIcon(System.getProperty("user.dir") + String.format("\\classes\\logo1.png", logoPicked))); 
		picLabel.setBounds(0, 0, 392, 471);
//		System.out.println(logoPicked);
		leftPanelMain.add(picLabel);

		/***************************************************************
		 * Left Panel - Cancel button (for future use)
		 ****************************************************************/
//		JButton btnCancel = new JButton("Cancel");
//		btnCancel.setBounds(202, 425, 190, 23);
//		leftPanelMain.add(btnCancel);
//		btnCancel.setVisible(false);

		/***************************************************************
		 * Right Panel - Home
		 ****************************************************************/
		JPanel rightPanelMain = new JPanel();
		rightPanelMain.setBounds(396, 0, 400, 471);
		rightPanelMain.setBackground(SystemColor.activeCaption);
		getContentPane().add(rightPanelMain);
		rightPanelMain.setLayout(null);

		/***************************************************************
		 * Right Panel - Generate Meal Plan Button
		 ****************************************************************/
		JButton btnGenerateMeal = new JButton("Generate Meal Plan");
		btnGenerateMeal.setBounds(105, 103, 190, 23);
		rightPanelMain.add(btnGenerateMeal);
		btnGenerateMeal.addActionListener(event -> {
			try {
				//Original button function
				//new GeneratePlanScreen(mealDatabase, this);

				// Rebuild Panel
//				leftPanelMain.removeAll();
				rightPanelMain.removeAll();
//				leftPanelMain.add(new GeneratePlanGUI());
				rightPanelMain.add(new RightPanelLogo());
//				leftPanelMain.validate();
				rightPanelMain.validate();
//				leftPanelMain.repaint();
				rightPanelMain.repaint();
							
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Generate Plan button!");
			}
		});

		/***************************************************************
		 * Right Panel - Change Database Button
		 ****************************************************************/
		JButton btnChangeDB = new JButton("Change Database");
		btnChangeDB.setBounds(105, 137, 190, 23);
		rightPanelMain.add(btnChangeDB);
		btnChangeDB.addActionListener(event -> {
			try {
				dispose();
				new Main(false);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Change Database button!");
			}
		});

		/***************************************************************
		 * Right Panel - Enter A Meal Button
		 ****************************************************************/
		JButton btnEnterMeal = new JButton("Enter a Meal");
		btnEnterMeal.setBounds(105, 171, 190, 23);
		rightPanelMain.add(btnEnterMeal);
		btnEnterMeal.addActionListener(event -> {
			try {
				new EnterMealScreen(mealDatabase, this);
            // rightPanel.setVisible(false);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Enter Meal button!");
			}
		});

		/***************************************************************
		 * Right Panel - Edit A Meal Button
		 ****************************************************************/
		JButton btnEditMeal = new JButton("Edit a Meal");
		btnEditMeal.setBounds(105, 205, 190, 23);
		rightPanelMain.add(btnEditMeal);
		btnEditMeal.addActionListener(event -> {
			try {
				new EditMealOptions(mealDatabase, this);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Edit meal button!");
			}
		});

		/***************************************************************
		 * Right Panel - Export Database to Text Button
		 ****************************************************************/
		JButton btnExport = new JButton("Export Database to Text");
		btnExport.setBounds(105, 239, 190, 23);
		rightPanelMain.add(btnExport);
		btnExport.addActionListener(event -> {
			try {
				new Database.DatabaseParser(mealDatabase, false);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Export button!");
			}
		});

		/***************************************************************
		 * Right Panel - Import Database from Text Button
		 ****************************************************************/
		JButton btnImport = new JButton("Import Database from Text");
		btnImport.setBounds(105, 273, 190, 23);
		rightPanelMain.add(btnImport);
		btnImport.addActionListener(event -> {
			try {
				new Database.DatabaseParser(mealDatabase, true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Import button!");
			}
		});

		/***************************************************************
		 * Right Panel - Quit Button
		 ****************************************************************/
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(105, 307, 190, 23);
		rightPanelMain.add(btnQuit);
		btnQuit.addActionListener(event -> {
			try {
				JOptionPane.showMessageDialog(null, "Thank you for using the Meal Plan Generator!", "Quitting Program",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Quit button!");
			}

		});

		/***************************************************************
		 * Right Panel - Labels to display database size
		 ****************************************************************/
		JLabel lblSelectOption = new JLabel("Select an Option\r\n");
		lblSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectOption.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 28));
		lblSelectOption.setBounds(10, 27, 380, 38);
		rightPanelMain.add(lblSelectOption);

		JLabel lblMealsInDB = new JLabel("Number of meals in database: " + mealDatabase.getDatabaseCount()[3]);
		lblMealsInDB.setBounds(10, 396, 200, 14);
		rightPanelMain.add(lblMealsInDB);

		JLabel lblSelectedDB = new JLabel("Selected Database: " + mealDatabase.getDatabaseName());
		lblSelectedDB.setBounds(10, 371, 200, 14);
		rightPanelMain.add(lblSelectedDB);

		JLabel lblBreakfasts = new JLabel("Breakfasts: " + mealDatabase.getDatabaseCount()[0]);
		lblBreakfasts.setBounds(220, 371, 170, 14);
		rightPanelMain.add(lblBreakfasts);

		JLabel lblLunches = new JLabel("Lunches: " + mealDatabase.getDatabaseCount()[1]);
		lblLunches.setBounds(220, 396, 170, 14);
		rightPanelMain.add(lblLunches);

		JLabel lblDinners = new JLabel("Dinners: " + mealDatabase.getDatabaseCount()[2]);
		lblDinners.setBounds(220, 421, 170, 14);
		rightPanelMain.add(lblDinners);

		// Timer that constantly displays updates to the database size
		new Timer(500, (ActionEvent ignored) -> {
			lblMealsInDB.setText("Number of meals in database: " + mealDatabase.getDatabaseCount()[3]);
			lblBreakfasts.setText("Breakfasts: " + mealDatabase.getDatabaseCount()[0]);
			lblLunches.setText("Lunches: " + mealDatabase.getDatabaseCount()[1]);
			lblDinners.setText("Dinners: " + mealDatabase.getDatabaseCount()[2]);
		}).start();
	}
	
//	public void rebuildPanel(JPanel newLeft, JPanel newRight) {
//		leftPanelMain.removeAll();
//		rightPanelMain.removeAll();
//		leftPanelMain.add(newLeft);
//		rightPanelMain.add(newRight);
//		leftPanelMain.validate();
//		rightPanelMain.validate();
//		leftPanelMain.repaint();
//		rightPanelMain.repaint();
//	}
	
	public void buildMainMenu() {
		
	}
}