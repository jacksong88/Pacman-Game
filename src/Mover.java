/**
 * This class is an abstract template class that extends the Jlabel class.
 * Holds the mover row, column, direction and status.
 * 
 */

//import external classes or packages
import javax.swing.*;

public abstract class Mover extends JLabel {

	// Fields
	// current position, row and column of Mover
	private int row;
	private int column;

	// change in row and column depending on direction
	private int dRow;
	private int dColumn;

	// if Mover object is dead or not
	private boolean isDead;

	// getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getdRow() {
		return dRow;
	}

	public void setdRow(int dRow) {
		this.dRow = dRow;
	}

	public int getdColumn() {
		return dColumn;
	}

	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	// UTILITY METHODS

	// move by adding the change in row and column
	public void move() {
		
		row += dRow;
		column += dColumn;
		
	}

	// set the direction of the Mover object
	// 0-left, 1-up, 2-right, 3-down
	public void setDirection(int direction) {

		//reset change in row and column to 0.
		dRow = 0;
		dColumn = 0;

		if (direction == 0) // left
			dColumn = -1;
		else if (direction == 1) // up
			dRow = -1;
		else if (direction == 2) // right
			dColumn = 1;
		else if (direction == 3) // down
			dRow = 1;

	}

	// get the direction of the mover object
	public int getDirection() {

		if (dRow == 0 && dColumn == -1) // moving left
			return 0;
		else if (dRow == -1 && dColumn == 0) // moving up
			return 1;
		else if (dRow == 0 && dColumn == 1) // moving right
			return 2;
		else // moving down
			return 3;
		
	}

	// returns the next row value
	public int getNextRow() {

		return row + dRow;

	}

	// returns the next column value
	public int getNextColumn() {

		return column + dColumn;

	}
}
