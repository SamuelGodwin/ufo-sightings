/**
 * 
 */
package section2.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

import section2.view2.SightingsWindow;

/**
 * 
 * @author Rihem
 * 
 * A class that implements MouseListener interface and overrides mouseClicked 
 * in order to set the action that should be performed when the mouse is double clicked on one of the JList's values
 * a parameter of type SightingsWindow is passed to the constructor of this class
 *
 */
public class ListListener implements MouseListener {
	
	private SightingsWindow sightingsWindow;
	
	public ListListener(SightingsWindow sightingsWindow){
		this.sightingsWindow=sightingsWindow;
		
	}


	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount()==2){
			 	
			sightingsWindow.messagePopUp( (String)(((JList)e.getSource()).getSelectedValue()) );
			 	
	}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
