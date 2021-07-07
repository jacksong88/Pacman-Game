/**
 * This class represents the information panel which displays
 * the number of lives left and if cherry has been collected or not
 */

//import external classes or packages
import java.awt.*;
import javax.swing.*;

public class InformationPanel extends JPanel {

	//create JLabel array for lives, only two lives
	public static JLabel[] lives = new JLabel[3];

	//create JLabel for cherry icon
	public static JLabel cherryIconLabel = new JLabel();

	//constructor that creates score label
	public InformationPanel() {

		//set layout, background color, and bounds
		setLayout(null);
		setBackground(Color.BLACK);

		//loop through each element in lives array and 
		//set appropriate properties
		//also adds it to the panel
		for (int index = 0; index < 3; index++) {

			//initialize element as JLabel
			lives[index] = new JLabel();

			//set image icon
			lives[index].setIcon(new ImageIcon("images/PacLeftOpen.bmp"));

			//set bounds
			lives[index].setBounds(25 + index * 25, 0, 25, 25);

			//add it onto the panel
			add(lives[index]);
			
		}

		//set icon for cherry icon label
		cherryIconLabel.setIcon(new ImageIcon("images/Cherry.bmp"));

		//setbounds
		cherryIconLabel.setBounds(550, 0, 25, 25);

		//add it to panel
		add(cherryIconLabel);
		
	}

}
