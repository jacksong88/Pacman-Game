/**
 * This class is used to create a PacMan object. It extends the mover class
 * and includes an array of ImageIcons Constants for the various states of pacman during animation,,
 * There is also a contructor that sets the inital image of PacMan.
 */

//import external class
import javax.swing.ImageIcon;

public class PacMan extends Mover {

	//2d array of imagesIcon for the 4 directions with open and closed images.
	public static final ImageIcon[][] IMAGE = {

			{ new ImageIcon("Images/PacLeftClosed.bmp"), new ImageIcon("Images/PacLeftOpen.bmp") },
			{ new ImageIcon("Images/PacUpClosed.bmp"), new ImageIcon("Images/PacUpOpen.bmp") },
			{ new ImageIcon("Images/PacRightClosed.bmp"), new ImageIcon("Images/PacRightOpen.bmp") },
			{ new ImageIcon("Images/PacDownClosed.bmp"), new ImageIcon("Images/PacDownOpen.bmp") }

	};

	//pacman constructor
	public PacMan() {

		setIcon(IMAGE[0][0]); //starts with pacman facing left with a closed mouth.

	}
}
