/**
 * This class is used to create Ghost objects.
 * It includes a constant ImageIcon array to hold the various ghost pictures
 * and a constructor method that sets the Ghosts' images
 *
 */

//import external class
import javax.swing.ImageIcon;

public class Ghost extends Mover {

	//fields
	private int ghostNum; //keep track the ghost number

	//imagesIcon array for each of the three ghosts
	public static final ImageIcon[] IMAGE = {

			new ImageIcon("Images/Ghost1.bmp"), new ImageIcon("Images/Ghost2.bmp"),
			new ImageIcon("Images/Ghost3.bmp") };

	//ghost constructor
	public Ghost(int ghostNum) {

		setIcon(IMAGE[ghostNum]);
		this.ghostNum = ghostNum;
		
	}

	//this method returns the ghost number
	public int getGhostNumber() {
		
		return ghostNum;
		
	}
}
