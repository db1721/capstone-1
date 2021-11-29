package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Utilities.RandomNumber;

public class RightPanelLogo extends JPanel {

	private static final long serialVersionUID = 1L;

	public RightPanelLogo() {
		setBounds(new Rectangle(396, 0, 400, 471));
		initGUI();
	}

	/**
	 * Create the frame.
	 */
	public void initGUI() {
		/***************************************************************
		 * Right Panel - Logo
		 ****************************************************************/
		setBackground(Color.PINK);	
		setBounds(396, 0, 400, 471);
		setLayout(null);
		setVisible(true);
		
		/***************************************************************
		 * Right Panel - Logo
		 ****************************************************************/
		// Dan playing around with random logo selection
		RandomNumber logoPicker = new RandomNumber(3); // how many logos are in the classes folder?
		int logoPicked = logoPicker.getNewNum();
		JLabel picLabel = new JLabel(
				// add .getNewNum here when figured out. Also add or remove the "%d" after logo
				new ImageIcon(System.getProperty("user.dir") + String.format("\\classes\\logo1.png", logoPicked))); 
		picLabel.setBounds(396, 0, 400, 471);
//		picLabel.setLayout(null);
		picLabel.setVisible(true);
//		System.out.println(logoPicked);
		this.add(picLabel);
		
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
		dropdownDaySelector.setBounds(86, 67, 220, 50);
		dropdownDaySelector.setSelectedIndex(0);
		this.add(dropdownDaySelector);
	}

}