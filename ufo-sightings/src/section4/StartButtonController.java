/**
 * 
 */
package section4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Controller class for start button; calls methods that start the game when button is clicked
 */
public class StartButtonController implements ActionListener {

	private Panel4 gameboard;

	/**
	 * Constructor sets reference of Panel4
	 * 
	 * @param gameboard
	 */
	public StartButtonController(Panel4 gameboard) {
		this.gameboard = gameboard;
	}

	/**
	 * actionPerformed overwritten method calls methods to start game when button is clicked
	 */
	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();
		gameboard.clear();
		gameboard.setupGame();
	}

}
