/**
 * This class represents the starting screen where the user can press space
 * to begin the game.
 */

//import external classes or packages
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreen extends JFrame implements KeyListener, ActionListener {

	//pacman logo image icon
	private ImageIcon pacmanLogo;

	//pacman logo label
	private JLabel pacmanLogoLabel = new JLabel();

	//starting message label
	private JLabel startingMessageLabel = new JLabel();

	//mute/unmute button
	private JButton muteButton = new JButton();

	//custom font for score
	public static Font emulogic;

	//if user wants to mute the game or not
	public static boolean ifMute = false;

	//constructor to add elements and set properties to the frame
	public StartScreen() {

		//set size, title and background colour of the frame
		setSize(615, 700);
		setTitle("PacMan");
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//set layout to null
		setLayout(null);

		//creates the custom font
		createFont();

		//setbounds for labels
		pacmanLogoLabel.setBounds(0, 25, 595, 200);
		startingMessageLabel.setBounds(100, 350, 500, 20);

		//setbounds for mute button
		muteButton.setBounds(200, 500, 200, 70);

		//set text, and font for mute button
		muteButton.setText("MUTE GAME");
		muteButton.setFont(emulogic.deriveFont(15f));

		//remove boarders around text
		muteButton.setFocusable(false);

		//scale and opens the logo image file, sets it to label.
		scalePacmanLogo();
		pacmanLogoLabel.setIcon(pacmanLogo);

		//set text colour, font, and text for startingMessageLabel
		startingMessageLabel.setForeground(Color.WHITE);
		startingMessageLabel.setFont(emulogic);
		startingMessageLabel.setText("PRESS ANY KEY TO START");

		//add labels and buttons to frame
		add(pacmanLogoLabel);
		add(startingMessageLabel);
		add(muteButton);

		//add key listener and action listener
		addKeyListener(this);
		muteButton.addActionListener(this);

		//make the frame visible
		setVisible(true);
	}

	//this class opens and scales the pacman logo image to fit the label
	private void scalePacmanLogo() {

		//initialize buffered image
		BufferedImage image = null;

		//try to open the image, catches errors
		try {
			
			image = ImageIO.read(new File("images/pacmanlogo.jpg"));
			
		} catch (IOException e) {
			
			System.out.println("Error opening image");
			
		}

		//scale the image to logo label's width and height
		Image scaledImage = image.getScaledInstance(pacmanLogoLabel.getWidth(), pacmanLogoLabel.getHeight(),
				Image.SCALE_SMOOTH);

		//set pacmanlogo to the new scaled image
		pacmanLogo = new ImageIcon(scaledImage);

	}

	//this method loads and creates a custom font
	public static void createFont() {

		//load in custom font
		//also catches errors
		try {

			//load in custom font from files
			emulogic = Font.createFont(Font.TRUETYPE_FONT, new File("fonts\\emulogic.ttf")).deriveFont(18f);

			//create a graphics environment to register the font
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

			//register the font
			graphicsEnvironment.registerFont(emulogic);

		} catch (IOException e) {
			
			System.out.println("Error occured...");
			
		} catch (FontFormatException e) {
			
			System.out.println("Error occured...");
			
		}

	}

	//handles button presses
	@Override
	public void actionPerformed(ActionEvent event) {

		//if mute button is clicked
		if (event.getSource() == muteButton) {

			//change mute to unmute or unmute to mute
			ifMute = !ifMute;

			//if it is mute, button text will change to unmute.
			//if it is ummute, button text will change to mute.
			//prompts the user what clicking the button will do with button text.
			if (ifMute)
				muteButton.setText("UNMUTE GAME");
			
			else
				muteButton.setText("MUTE GAME");
			
		}

	}

	//handles keyboard entries, to start the game
	@Override
	public void keyPressed(KeyEvent key) {

		//starts the game when any key is pressed
		if (key.getKeyCode() > 0) {
			
			dispose(); //closes the current window
			new PacManGUI();
			
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

}
