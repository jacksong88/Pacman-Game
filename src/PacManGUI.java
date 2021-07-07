/**
 * 
 * This class creates a PacMan GUI that extends the JFrame class. It has a Board (JPanel) and
 * includes a constructor method that sets up the game and adds a key listener to the board.
 *
 */

//import external classes or packages
import javax.swing.*;

public class PacManGUI extends JFrame {

	//create a Board object for the board on screen
	private Board board = new Board();

	//create a ScoreBoard object for the score board on screen
	private ScoreBoard scoreBoard = new ScoreBoard();

	//create information panel object to display game information 
	private InformationPanel informationPanel = new InformationPanel();

	//constructor to add elements and set properties to the frame
	public PacManGUI() {

		//set size and title of the frame
		setSize(615, 750);
		setTitle("PacMan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//set layout to null
		setLayout(null);

		//set bounds for panels
		board.setBounds(0, 55, 600, 600);
		scoreBoard.setBounds(0, 0, 600, 80);
		informationPanel.setBounds(0, 655, 600, 50);

		//add the score board to the frame
		add(scoreBoard);

		//add board to the frame, also listens to keyboard key input.
		addKeyListener(board);
		add(board);

		//add information panel to frame
		add(informationPanel);

		//make the frame visible
		setVisible(true);
		
	}

}
