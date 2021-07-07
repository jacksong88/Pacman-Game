/**
 * This class represents the score board and includes methods
 * to handle the overall score 
 */

//import external classes or packages
import java.awt.*;
import javax.swing.*;

public class ScoreBoard extends JPanel {

	//label to display the current score
	//static so Board class can access it
	public static JLabel scoreLabel = new JLabel();

	//label to display the high score
	//static so Board class can access it
	public static JLabel highscoreLabel = new JLabel();

	//constructor that creates score label
	public ScoreBoard() {

		//set layout, background color, and bounds
		setLayout(null);
		setBackground(Color.BLACK);

		//set bounds, background and foreground for score label. Add it to the panel
		scoreLabel.setBounds(25, 25, 200, 20);
		scoreLabel.setBackground(Color.BLACK);
		scoreLabel.setForeground(Color.WHITE);

		//set starting score to 0
		scoreLabel.setText("Score: 0");

		//set the font to appropriate labels
		scoreLabel.setFont(StartScreen.emulogic);

		//set bounds, background and foreground for higjscore label. Add it to the panel
		highscoreLabel.setBounds(290, 25, 300, 20);
		highscoreLabel.setBackground(Color.BLACK);
		highscoreLabel.setForeground(Color.WHITE);

		//set starting score to 0
		highscoreLabel.setText("High Score: " + Board.highscore);

		//set the font to appropriate labels
		highscoreLabel.setFont(StartScreen.emulogic);

		//add score and highscore label to panel
		add(scoreLabel);
		add(highscoreLabel);

	}

}
