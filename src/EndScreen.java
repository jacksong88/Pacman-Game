/**
 * This class represents the ending screen where it displays to the user
 * the results of the game after it finishes
 */

//import external classes or packages
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EndScreen extends JFrame implements ActionListener {

	//end message label
	private JLabel endMessageLabel = new JLabel();

	//final score label
	private JLabel finalScoreLabel = new JLabel();

	//high score label
	private JLabel highScoreLabel = new JLabel();

	//button can be pressed if user wants to play again
	private JButton playAgainButton = new JButton();

	//constructor to add elements and set properties to the frame
	public EndScreen() {

		//set size, title and background colour of the frame
		setSize(615, 700);
		setTitle("PacMan");
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//set layout to null
		setLayout(null);

		//set properties for all labels
		//end message label
		endMessageLabel.setForeground(Color.WHITE);
		endMessageLabel.setFont(StartScreen.emulogic.deriveFont(23f));
		setEndMessageLabelText();

		//final score label
		finalScoreLabel.setBounds(110, 250, 500, 50);
		finalScoreLabel.setForeground(Color.WHITE);
		finalScoreLabel.setFont(StartScreen.emulogic.deriveFont(20f));

		//set text to display the final score
		finalScoreLabel.setText("Final Score: " + Board.score + "pts");

		//high score label
		highScoreLabel.setBounds(115, 400, 500, 50);
		highScoreLabel.setForeground(Color.WHITE);
		highScoreLabel.setFont(StartScreen.emulogic.deriveFont(20f));

		//set text to display the high score
		highScoreLabel.setText("High Score: " + Board.highscore + "pts");

		//set properties for play again button
		playAgainButton.setBounds(140, 550, 300, 50);
		playAgainButton.setFont(StartScreen.emulogic);
		playAgainButton.setText("PLAY AGAIN");
		playAgainButton.setFocusable(false);

		//add labels to frame
		add(endMessageLabel);
		add(finalScoreLabel);
		add(highScoreLabel);

		//add button to frame
		add(playAgainButton);

		//add action listener
		playAgainButton.addActionListener(this);

		//make the frame visible
		setVisible(true);

	}

	//set end message text depending on the ending
	//html used to center text
	private void setEndMessageLabelText() {

		//if pacman lost all lives
		//set bounds for correct alignment
		if (Board.endingType == 0) {
			
			endMessageLabel.setBounds(180, 100, 300, 50);
			endMessageLabel.setText("<html><div style='text-align: center;'> GAME OVER<html>");
			
		}

		//if pacman ate all pellets
		//set bounds for correct alignment
		else if (Board.endingType == 1) {
			
			endMessageLabel.setBounds(10, 100, 600, 50);
			endMessageLabel.setText("VICTORY! CONGRATULATIONS!");
			
		}

	}

	//this method handles button presses and performs appropriate actions
	@Override
	public void actionPerformed(ActionEvent event) {

		//if user clicks the button
		//start the game over again
		//closes current window
		if (event.getSource() == playAgainButton) {
			
			dispose();
			Board.ifPlayAgain = true;
			new PacManGUI();
			
		}

	}

}