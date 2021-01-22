
package section2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import section2.view2.Panel2;

/**
 * 
 * @author Rihem
 * 
 * A class that implements ActionListener interface and overrides actionPerformed(ActionEvent e) 
 * in order to set the action that should be performed when the button is clicked
 * a parameter of type Panel2 is passed to the constructor of this class
 *
 */
public class ButtonListener implements ActionListener {

	
	private Panel2 panel2;
	
	/**
	 * Constructor for ButtonListener, Panel2 is a parameter in this constructor
	 * @param panel2
	 */
	public ButtonListener(Panel2 panel2){
		 this.panel2=panel2;	
	}
	
	/**
	 * Method in which the action for the button clicked is set, it calls a calls a method from Panel2 that creates a 
	 * new object of SightingsWindow
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	
		panel2.createSightingsWindow(((JButton)e.getSource()).getName());
		
	}
}