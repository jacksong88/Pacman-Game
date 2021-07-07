/**
 * 
 * This program simulates Pac-Man game with alterations. The pac man, is controlled by the player 
 * to transverse through a constructed maze to eat all the food pellets . Once all food pellets have 
 * been eaten, it will result in a victory for the player. However, there are ghosts who are hunting
 * pacman and pacman will die if it comes in contact with a ghost. The player has a total of 3 lives
 * to collect all pellets and, if the player chooses to, to collect a bonus cherry. Each pellet is worth
 * 1 point, and the bonus cherry is worth 10.
*/

public class PacManGame {

	//main method 
	public static void main(String[] args) {

		//Start the game by creating a new PacManGUI class
		new StartScreen();

	}

}
