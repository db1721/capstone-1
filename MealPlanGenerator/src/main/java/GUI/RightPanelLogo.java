package GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Utilities.RandomNumber;

public class RightPanelLogo extends JPanel {

	private static final long serialVersionUID = 1L;

	public RightPanelLogo() {
		initGUI();
	}

	/**
	 * Create the frame.
	 */
	public void initGUI() {
		JPanel rightPanelLogo = new JPanel();
		rightPanelLogo.setBounds(0, 0, 392, 471);
		rightPanelLogo.setBackground(Color.WHITE);
		add(rightPanelLogo);
		rightPanelLogo.setLayout(new BorderLayout(0, 0));
		// Dan playing around with random logo selection
		RandomNumber logoPicker = new RandomNumber(3); // how many logos are in the classes folder?
		int logoPicked = logoPicker.getNewNum();
		// add .getNewNum here when figured out. Also add or remove the "%d" after logo
		JLabel picLabel = new JLabel(
				new ImageIcon(System.getProperty("user.dir") + String.format("\\classes\\logo1.png", logoPicked)));
//		System.out.println(logoPicked);
		rightPanelLogo.add(picLabel);
	}

}
