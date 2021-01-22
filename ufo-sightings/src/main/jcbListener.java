/**
 * 
 */
package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * This is the listener for our JComboBoxes in MainGUI.
 * 
 * @author Samuel & Sean
 *
 */
public class jcbListener implements ActionListener {

	
	private MainGUI maingui;

	/**
	 * Constructor method sets field 'maingui' to a passed reference of
	 * a MainGUI object.
	 * @param maingui
	 */
	public jcbListener(MainGUI maingui){
		this.maingui = maingui;
	}
	
	/**
	 * Calls jcbbutton in MainGUI.
	 */
	public void actionPerformed(ActionEvent arg0) {

		JComboBox combo = (JComboBox) arg0.getSource();
		String currentQuantity = (String) combo.getSelectedItem();

		maingui.jcbbutton();
	}
	
}
