/**
 * This class represents the game board and includes methods to 
 * handle keyboard events and game actions. Handles most of the important elements
 * of the game.
 */

//import external classes or packages
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;
import javax.swing.*;

public class Board extends JPanel implements KeyListener, ActionListener {

	//timer for game movement
	private Timer gameTimer = new Timer(300, this);

	//animation timer for pacman animation
	private Timer animateTimer = new Timer(40, this);

	//open and initialize wall icon as a constant
	private static final ImageIcon WALL = new ImageIcon("images/StdWall.bmp");

	//open and initialize food icon as a constant
	private static final ImageIcon FOOD = new ImageIcon("images/StdFood.bmp");

	//open and initialize blank square icon as a constant
	private static final ImageIcon BLANK = new ImageIcon("images/Black.bmp");

	//open and initialize door icon as a constant
	private static final ImageIcon DOOR = new ImageIcon("images/Black.bmp");

	//open and initialize skull icon as a constant
	private static final ImageIcon SKULL = new ImageIcon("images/Skull.bmp");

	//open and initialize cherry icon as a constant
	private static final ImageIcon CHERRY = new ImageIcon("images/Cherry.bmp");

	//holds the type of ending, 0 - no more lives, 1 - all pellets eaten 
	public static int endingType = -1;

	//create a 2d character array to store the game board characters of the maze.
	private char[][] maze = new char[25][27];

	//create a label for every cell in the maze
	private JLabel[][] cell = new JLabel[25][27];

	//array to store which pellets have been eaten
	private boolean[][] eaten = new boolean[25][27];

	//create a pacman object
	private PacMan pacMan;

	//create ghost array for all of the ghosts in the game
	private Ghost[] ghost = new Ghost[3];

	//to keep track the amount of food on the board
	private int pellets = 0;

	//stores the total number of pellets eaten
	private int pelletsEaten = 0;

	//keeps track of the score (1 point for every food item eaten)
	public static int score = 0;

	//keeps track of high score
	public static int highscore = 0;

	//steps for animating the Pacman chomp
	private int pStep;

	//number of lives pacman has left
	private int lives = 2;

	//if the bonus cherry item is collected or not
	private boolean hasCherryAppeared;

	//if starting music has played
	private boolean ifStartingMusicPlayed = false;

	//if user plays more than 1 time
	public static boolean ifPlayAgain = false;

	//constructor that creates the board layout, background Pacman and ghosts 
	//also calls loadBoard method
	public Board() {

		//set layout to GridLayout and the background black	
		//GridLayout makes it easier 
		setLayout(new GridLayout(25, 27));
		setBackground(Color.black);

		//initialize pacman object and ghost objects
		pacMan = new PacMan();

		ghost[0] = new Ghost(0);
		ghost[1] = new Ghost(1);
		ghost[2] = new Ghost(2);

		//load the maze onto the screen
		loadBoard();

		//reset values if user plays for more than 1 times
		if (ifPlayAgain) {

			hasCherryAppeared = false;
			ifStartingMusicPlayed = false;
			lives = 2;
			score = 0;
			pelletsEaten = 0;
			endingType = -1;

		}

		//start the game timer
		gameTimer.start();

	}

	//loads the maze onto the screen by reading it from a text file
	private void loadBoard() {

		//row number
		int row = 0;

		//Create a scanner object
		Scanner input;

		//try to read in the file using Scanner, catches errors
		try {

			//initialize Scanner to read Maze text file
			input = new Scanner(new File("Maze.txt"));

			//while there is still a line to read, the program will continue
			while (input.hasNext()) {

				//convert each line to a charArray and stores it in maze array
				maze[row] = input.nextLine().toCharArray();

				//loop through each column of the current row
				for (int column = 0; column < maze[row].length; column++) {

					//initialize each element in cell array as a JLabel
					cell[row][column] = new JLabel();

					//set wall ImageIcons
					if (maze[row][column] == 'W')

						cell[row][column].setIcon(WALL);

					//set food ImageIcons
					else if (maze[row][column] == 'F') {

						cell[row][column].setIcon(FOOD);

						//keeps track of the amount of food
						pellets++;

					}

					//set Blank ImageIcons
					else if (maze[row][column] == 'X')

						cell[row][column].setIcon(BLANK);

					//set Door ImageIcons
					else if (maze[row][column] == 'D')

						cell[row][column].setIcon(DOOR);

					//set Pacman ImageIcon and location
					else if (maze[row][column] == 'P') {

						cell[row][column].setIcon(pacMan.getIcon());

						//set Pacman row and column to location of 'P'
						pacMan.setRow(row);
						pacMan.setColumn(column);

						//starts facing left
						pacMan.setDirection(0);
					}

					//set Ghosts ImageIcons and location
					else if (maze[row][column] == '0' || maze[row][column] == '1' || maze[row][column] == '2') {

						//convert the char to an equivalent integer
						int ghostNum = Character.getNumericValue(maze[row][column]);

						cell[row][column].setIcon(ghost[ghostNum].getIcon()); //set icon

						//set row and column location for each ghost
						ghost[ghostNum].setRow(row);
						ghost[ghostNum].setColumn(column);

					}

					//add current cell to allow it to display on screen
					add(cell[row][column]);

				}

				//move onto the next row
				row++;
			}

			//close file input
			input.close();

		} catch (FileNotFoundException error) {

			//displays error message when file cannot be found
			System.out.println("File not found.");
		}
	}

	//handles keyboard entries - to start the game and control movement of pacman
	@Override
	public void keyPressed(KeyEvent key) {

		//if pacman is not dead or all pellets have not been eaten
		//update pacman depending on type of moment
		if (pacMan.isDead() == false && pelletsEaten != pellets) {

			//get which key is pressed, up, down, left or right
			int direction = key.getKeyCode() - 37;

			//change pacman's direction respective to the key pressed
			if (direction == 0 && maze[pacMan.getRow()][pacMan.getColumn() - 1] != 'W') //left
				pacMan.setDirection(0);
			else if (direction == 1 && maze[pacMan.getRow() - 1][pacMan.getColumn()] != 'W') //up
				pacMan.setDirection(1);
			else if (direction == 2 && maze[pacMan.getRow()][pacMan.getColumn() + 1] != 'W') //down
				pacMan.setDirection(2);
			else if (direction == 3 && maze[pacMan.getRow() + 1][pacMan.getColumn()] != 'W') //right
				pacMan.setDirection(3);
		}
	}

	//must remain to satisfy the KeyListener code interface
	@Override
	public void keyReleased(KeyEvent key) {
		//not used, needed in order to run the program
	}

	//must remain to satisfy the KeyListener code interface
	@Override
	public void keyTyped(KeyEvent key) {
		//not used, needed in order to run the program

	}

	//allows an object in the game to move and updates it both on the maze and screen 
	//based on which object it is, the direction it is facing and the change in row 
	//and column value
	//
	//param: mover to either move pacman or a ghost
	private void performMove(Mover mover) {

		//if an object goes into a door
		if (mover.getColumn() == 1) {
			mover.setColumn(24);
			cell[12][1].setIcon(DOOR);
		} else if (mover.getColumn() == 25) {
			mover.setColumn(2);
			cell[12][25].setIcon(DOOR);
		}

		//if the next place an object will go is not a wall
		if (maze[mover.getNextRow()][mover.getNextColumn()] != 'W') {

			//if the mover object is referring to pacMan
			//makes sure that pacman doesn't go through the ghost gate into the ghost room.
			if (mover == pacMan) {
				if (maze[mover.getNextRow()][mover.getNextColumn()] != 'X')
					animateTimer.start();

				//if pacman collides with a ghost, it will die
				if (collided())
					death(); //kills pacman
			}

			//otherwise, if mover object is referring to a ghost
			else {

				//make sure ghosts do not eat the food pellets, only pacman can
				if (maze[mover.getRow()][mover.getColumn()] == 'F')
					cell[mover.getRow()][mover.getColumn()].setIcon(FOOD);

				//make sure ghosts do not eat the cherry, only pacman can
				else if (maze[mover.getRow()][mover.getColumn()] == 'C')
					cell[mover.getRow()][mover.getColumn()].setIcon(CHERRY);

				//Make the cell blank if it already is. 
				else
					cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);

				//move the ghost
				mover.move();

				//if a ghost collides with pacman, it will die
				if (collided())
					death(); //kills pacman

				//otherwise, set the next location Icon to the current ghost's icon
				else
					cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());
			}
		}
	}

	//determines if pacman has collided with a ghost
	//returns true or false
	private boolean collided() {

		//loops through every ghost in ghost array
		for (Ghost ghostNum : ghost) {

			//if ghost goes on top of pacman and collides with each other
			// return true
			if (ghostNum.getRow() == pacMan.getRow() && ghostNum.getColumn() == pacMan.getColumn())
				return true;
		}

		//if no ghost is colliding with pacman, return false.
		return false;
	}

	//Kills pacman and stops the game when pacman collides with a ghost
	private void death() {

		//play death sound
		playSound("sounds/killed.wav");

		//stops the game, game over.
		resetPacman();

	}

	//this method resets pacman and ghosts to the starting location
	private void resetPacman() {

		//if pacman still has lives, reset his position back to starting
		//otherwise, stop the game
		if (lives > 0) {

			//set current location to blank
			//makes sure ghosts doesn't erase any pellets or cherry on screen
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);

			for (int index = 0; index < 3; index++) {

				//if ghost is on top of food pellet during reset, makes sure it doens't turn blank
				if (maze[ghost[index].getRow()][ghost[index].getColumn()] == 'F'
						&& !eaten[ghost[index].getRow()][ghost[index].getColumn()])
					cell[ghost[index].getRow()][ghost[index].getColumn()].setIcon(FOOD);

				//if ghost is on cherry
				else if (maze[ghost[index].getRow()][ghost[index].getColumn()] == 'C')
					cell[ghost[index].getRow()][ghost[index].getColumn()].setIcon(CHERRY);

				//otherwise, if ghost isn't on any food items
				else
					cell[ghost[index].getRow()][ghost[index].getColumn()].setIcon(BLANK);

			}

			//reset positions and directions of pacman and ghosts

			//pacman
			pacMan.setRow(15);
			pacMan.setColumn(13);
			pacMan.setDirection(0);

			//ghost 0
			ghost[0].setRow(11);
			ghost[0].setColumn(11);
			ghost[0].setDirection(2);

			//ghost 1
			ghost[1].setRow(13);
			ghost[1].setColumn(13);
			ghost[1].setDirection(1);

			//ghost 2
			ghost[2].setRow(11);
			ghost[2].setColumn(15);
			ghost[2].setDirection(0);

			//delays for 3 seconds
			delay(3);

		} else {

			//set pacman to dead
			pacMan.setDead(true);
			stopGame();

		}

		//subtract lives by 1
		lives--;

		//update lives icon in information panel
		//also prevents index out of bounds exception
		if (lives >= 0)
			InformationPanel.lives[lives].setIcon(BLANK);

	}

	//stops the game and game timer.
	private void stopGame() {

		//prevents multiple end screens when there is double collision
		if (endingType == 0 || endingType == 1)
			return;

		//if pacman lost all lives
		//set ending type
		if (pacMan.isDead()) {

			endingType = 0;

			//stop game and animate timers
			animateTimer.stop();
			gameTimer.stop();

			//go to ending Screen, closes the current window
			SwingUtilities.getWindowAncestor(this).dispose();
			new EndScreen();

		}

		//if pacman ate all the pellets on the screen
		//play victory sounds and set ending type
		if (pelletsEaten == pellets) {

			endingType = 1;
			playSound("sounds/victory.wav");

			//stop game and animate timers
			animateTimer.stop();
			gameTimer.stop();

			//go to ending Screen, closes the current window
			SwingUtilities.getWindowAncestor(this).dispose();
			new EndScreen();

		}

		//stop the game timer and animate timer
		//whether its because pacman has no more lives
		//or all pellets have been eaten
		animateTimer.stop();
		gameTimer.stop();

	}

	//move the ghosts on the screen
	private void moveGhosts() {

		//loop through each ghost in ghost array
		for (Ghost ghostNum : ghost) {

			int direction = 0; //moving direction

			//if ghost is still in ghost room, helps it get out
			if (ghostNum.getRow() >= 10 && ghostNum.getRow() <= 14 && ghostNum.getColumn() >= 10
					&& ghostNum.getColumn() <= 16)
				direction = escapeGhostRoom(ghostNum); //sets the optimal direction for escaping ghost room		

			//otherwise, performs the ghost AI
			else
				direction = ghostAI(ghostNum);

			//set ghost direction
			ghostNum.setDirection(direction);

			//move the ghost
			performMove(ghostNum);

		}
	}

	//this method returns the optimal direction a ghost should face to escape the room at the beginning.
	private int escapeGhostRoom(Ghost ghost) {

		//check to see which ghost it is then return the direction the ghost needs to move 
		//to reach near the exit of the ghost room.
		if (ghost.getGhostNumber() == 0) {

			if (ghost.getRow() == 11 && (ghost.getColumn() == 11 || ghost.getColumn() == 12))
				return 2;

		} else if (ghost.getGhostNumber() == 2) {

			if (ghost.getRow() == 11 && (ghost.getColumn() == 14 || ghost.getColumn() == 15))
				return 0;

		}

		//After it reaches close to the ghost door, precisely row 11 and column 13 (0 indexed)
		//All ghosts should face the up direction and leave the ghost room. 
		//Redirects the ghost in the opposite direction if it tries to enter the ghost room again.
		//Ghost 1 located at bottom middle only needs to head straight up. 
		return 1;

	}

	//this method runs a ghost AI algorithm and returns the direction the ghost should face.
	private int ghostAI(Ghost ghost) {

		int currentDirection = ghost.getDirection(); //current direction ghost is facing
		int newDirection; //new direction ghost will face
		ArrayList<Integer> directions = new ArrayList<Integer>(); //Arraylist to store possible directions ghost can go

		//checks to see which direction the current ghost can go to without hitting a wall.
		//adds it to the directions array list
		//makes sure that the ghost will not face the opposite direction of the current direction
		//to ensure smoother movements
		if (maze[ghost.getRow()][ghost.getColumn() - 1] != 'W')
			if (Math.abs(currentDirection - 0) != 2)
				directions.add(0);

		if (maze[ghost.getRow() - 1][ghost.getColumn()] != 'W')
			if (Math.abs(currentDirection - 1) != 2)
				directions.add(1);

		if (maze[ghost.getRow()][ghost.getColumn() + 1] != 'W')
			if (Math.abs(currentDirection - 2) != 2)
				directions.add(2);

		if (maze[ghost.getRow() + 1][ghost.getColumn()] != 'W')
			if (Math.abs(currentDirection - 3) != 2)
				directions.add(3);

		//picks a random number between 0 to how many directions the ghost can go
		//the random number acts as an index in the directions array list
		//returns the direction picked in the arraylist. 
		newDirection = directions.get((int) (Math.random() * directions.size()));
		return newDirection;

	}

	// determines the source of the performed action either if its game timer or
	// animate timer.
	// performs appropriate actions
	@Override
	public void actionPerformed(ActionEvent event) {

		// if action source is game timer
		if (event.getSource() == gameTimer) {

			// move pacman and ghosts
			performMove(pacMan);
			moveGhosts();

			//if cherry is not collected yet
			//user has a chance for the cherry to appear 
			//after every move in game.
			if (!hasCherryAppeared) {

				//cherry has a 2% chance of appearing
				if ((int) (Math.random() * 50) + 1 == 5) {

					//make the cherry appear at a designated location
					maze[19][13] = 'C';
					cell[19][13].setIcon(CHERRY);

					//set hasCherryAppeared to true as it appeared on the board
					hasCherryAppeared = true;
				}

			}

			//at the beginning, play the starting sound
			//wait around 4 seconds to only allow the player to move
			//after the starting sound has played
			//also consumes the first live as player is able to control pacman
			if (!ifStartingMusicPlayed) {

				//set it to true so it doesn't play a second time
				ifStartingMusicPlayed = true;

				//play sound
				playSound("sounds/GAMEBEGINNING.wav");

				//delay for 4 seconds
				delay(4);

				//consumes the first live by only updating it on InformationPanel.
				InformationPanel.lives[2].setIcon(BLANK);
			}

		}

		// otherwise if its animate timer
		else if (event.getSource() == animateTimer) {

			// animate pacman
			animatePacMan();

			// increase animation step by 1
			pStep++;

			// if animation step is 3, reset to beginning step
			if (pStep == 3)
				pStep = 0;

		}

	}

	//this method delays the program for given amount of seconds
	private void delay(int seconds) {

		//delays for x seconds, catches errors
		try {

			TimeUnit.SECONDS.sleep(seconds);

		} catch (InterruptedException e) {

			System.out.println("Error delaying time");

		}

	}

	//animates pacman with three steps, open mouth, draw blank black square, move and then close mouth
	private void animatePacMan() {

		// if animation step is 0
		if (pStep == 0) {

			//set new image icon to pacman
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][1]);

			//delay animate for smoother animations
			animateTimer.setDelay(100);

		}

		// if animation step is 1
		else if (pStep == 1)
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);

		// if animation step is 2
		else if (pStep == 2) {

			//move pacman
			pacMan.move();

			//if pacman consumes a food pellet
			if (maze[pacMan.getRow()][pacMan.getColumn()] == 'F') {

				//increase score by 1
				score++;

				//increase pellets eaten counter by 1
				pelletsEaten++;

				//set new highscore if current score is higher
				//updates highscore label
				if (score > highscore) {

					highscore = score;
					ScoreBoard.highscoreLabel.setText("High Score: " + highscore);

				}

				//update score label text
				ScoreBoard.scoreLabel.setText("Score: " + score);

				//play eating sound
				playSound("sounds/pacchomp.wav");

				//set letter E to pacman's location in the maze array
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				//set eaten array at current row and column to true
				eaten[pacMan.getRow()][pacMan.getColumn()] = true;

			}

			//if pacman consumes a cherry
			else if (maze[pacMan.getRow()][pacMan.getColumn()] == 'C') {

				//increase score by 10
				score += 10;

				//set new highscore if current score is higher
				//updates highscore label
				if (score > highscore) {

					highscore = score;
					ScoreBoard.highscoreLabel.setText("High Score: " + highscore);

				}

				//update score label text
				ScoreBoard.scoreLabel.setText("Score: " + score);

				//play cherry eating sound
				playSound("sounds/fruiteat.wav");

				//update information panel
				InformationPanel.cherryIconLabel.setIcon(BLANK);

				//set letter E to pacman's location in the maze array
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				//set eaten array at current row and column to true
				eaten[pacMan.getRow()][pacMan.getColumn()] = true;
			}

			//stop animation timer
			animateTimer.stop();

			//if pacman goes on top of the ghost and collides.
			if (pacMan.isDead())
				stopGame();

			//otherwise set pacman to a close mouth.
			else
				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][0]);

			if (pelletsEaten == pellets)
				stopGame();
		}

	}

	//this method plays audio and sounds
	private void playSound(String filePath) {

		//if user wants to mute sounds
		//no sound will be played
		if (StartScreen.ifMute)
			return;

		//get sound file from file path
		File soundFile = new File(filePath);

		//try to play the audio catches any errors
		try {

			//load sound file into audioinputstream
			//Format the audio
			AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = audio.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			//try to clip the audio and play the audio
			//catches any errors. 
			try {

				Clip audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.open(audio);
				audioClip.start();

			} catch (LineUnavailableException e) {

				System.out.println("Error playing audio");

			}

		} catch (UnsupportedAudioFileException e) {

			System.out.println("Error playing audio");

		} catch (IOException e) {

			System.out.println("Error playing audio");

		}

	}

}
