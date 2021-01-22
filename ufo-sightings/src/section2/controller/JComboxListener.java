

/**
  * 
  */
  package section2.controller;
  
  import java.awt.event.ActionEvent;
  import java.awt.event.ActionListener;
  
  import section2.view2.SightingsWindow;
  
  /**
   * 
   * @author Rihem
   * 
   * A class that implements ActionListener interface and overrides actionPerformed
   * in order to set the action that should be performed when another option from the jComboBox is selected
   * a parameter of type SightingsWindow is passed to the constructor of this class
   *
   */
  public class JComboxListener implements ActionListener {
  	
  	private SightingsWindow sightingsWindow;
  	
  /**
	 * Constructor for ButtonListener, SightingsWindow is a parameter in this constructor
	 * @param panel2
	 */
  	public JComboxListener(SightingsWindow sightingsWindow){
  		this.sightingsWindow=sightingsWindow;
  		
  	}
  
  /**
	 * Method inside of which the action for the jComboBox is set, it calls a method from Panel2 that creates a 
	 * new object of SightingsWindow
	 */
  	@Override
  	public void actionPerformed(ActionEvent e) {
  		sightingsWindow.newSelection();
  
  	}
  
  }
