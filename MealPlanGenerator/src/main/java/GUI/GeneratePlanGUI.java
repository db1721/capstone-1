package GUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Rectangle;

public class GeneratePlanGUI extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 * 
	 * May have to pass in database
	 */
	public GeneratePlanGUI() {
		setBounds(new Rectangle(0, 0, 392, 471));
		initGUI();
	}

	/**
	 * Creates the frame.
	 * 
	 * @return
	 */
	public void initGUI() {
		/***************************************************************
		 * Left Panel - Generate Meal Plane
		 ****************************************************************/
		setBackground(Color.WHITE);
		setBounds(0, 0, 392, 471);
		setLayout(null);
		setVisible(true);

		/***************************************************************
		 * Day to start Selector
		 ****************************************************************/
		String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(days);
		setLayout(null);
		JComboBox<String> dropdownDaySelector = new JComboBox<String>(comboModel);

		dropdownDaySelector.setBackground(Color.WHITE);
		dropdownDaySelector.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Day To Start", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		dropdownDaySelector.setFont(new Font("Arial", Font.PLAIN, 12));
		dropdownDaySelector.setEditable(false);
		dropdownDaySelector.setBounds(10, 67, 220, 42);
		dropdownDaySelector.setSelectedIndex(0);
		this.add(dropdownDaySelector);

		/***************************************************************
		 * Sliders
		 ****************************************************************/
		JSlider sliderMealsToGenerate = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
		sliderMealsToGenerate.setFont(new Font("Arial", Font.PLAIN, 12));
		sliderMealsToGenerate.setBounds(10, 199, 306, 68);
		sliderMealsToGenerate.setMajorTickSpacing(1);
		sliderMealsToGenerate.setMinorTickSpacing(1);
		sliderMealsToGenerate.setPaintTicks(true);
		sliderMealsToGenerate.setPaintLabels(true);
		sliderMealsToGenerate.setBorder(BorderFactory.createTitledBorder("Meals Per Day"));
		sliderMealsToGenerate.setBackground(Color.WHITE);
		this.add(sliderMealsToGenerate);

		JSlider sliderDaysToGenerate = new JSlider(SwingConstants.HORIZONTAL, 1, 7, 7);
		sliderDaysToGenerate.setPaintTicks(true);
		sliderDaysToGenerate.setPaintLabels(true);
		sliderDaysToGenerate.setMinorTickSpacing(1);
		sliderDaysToGenerate.setMajorTickSpacing(1);
		sliderDaysToGenerate.setFont(new Font("Arial", Font.PLAIN, 12));
		sliderDaysToGenerate.setBorder(BorderFactory.createTitledBorder("Days to Generate"));
		sliderDaysToGenerate.setBackground(Color.WHITE);
		sliderDaysToGenerate.setBounds(10, 120, 306, 68);
		this.add(sliderDaysToGenerate);

		/***************************************************************
		 * Check boxes
		 ****************************************************************/
		JCheckBox chckbxBreakfast = new JCheckBox("Breakfast");
		chckbxBreakfast.setSelected(true);
		chckbxBreakfast.setBackground(Color.WHITE);
		chckbxBreakfast.setBounds(10, 37, 89, 23);
		this.add(chckbxBreakfast);

		JCheckBox chckbxLunch = new JCheckBox("Lunch");
		chckbxLunch.setSelected(true);
		chckbxLunch.setBackground(Color.WHITE);
		chckbxLunch.setBounds(105, 37, 89, 23);
		this.add(chckbxLunch);

		JCheckBox chckbxDinner = new JCheckBox("Dinner");
		chckbxDinner.setSelected(true);
		chckbxDinner.setBackground(Color.WHITE);
		chckbxDinner.setBounds(200, 37, 89, 23);
		this.add(chckbxDinner);

		/***************************************************************
		 * Generate Plan Button
		 ****************************************************************/
		JButton btnGeneratePlan = new JButton("Generate Plan");
		btnGeneratePlan.setBounds(new Rectangle(0, 0, 10, 20));
		btnGeneratePlan.setBounds(10, 278, 220, 30);
		this.add(btnGeneratePlan);
		btnGeneratePlan.addActionListener(event -> {
			try {
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in Generate Plan Execute button!");
			}
		});

		/***************************************************************
		 * Labels
		 ****************************************************************/
		JLabel txtTitle = new JLabel("Meal Generation Settings");
		txtTitle.setFont(new Font("Arial", Font.PLAIN, 18));
		txtTitle.setBounds(10, 8, 203, 25);
		this.add(txtTitle);
	}
}
