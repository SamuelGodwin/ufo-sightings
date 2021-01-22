/**
 * 
 */
package section2.view2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JButton;

import javax.swing.JComponent;
import javax.swing.JPanel;


import ripleytest.RipleyData;
import section2.controller.ButtonListener;


/**
 * @author Rihem
 * This class models the map of the usa with alien buttons of different sizes based on the number
 * of incidents per state.
 *
 */
public class Panel2 extends JPanel{
	
	private BufferedImage map, alienimg;
	private ImageIcon alienIcon;
	private JPanel mapPanel= new JPanel();
	private MyPanel mapImage;
	private RipleyData ripleyData;

	private ArrayList<JButton> buttonList = new ArrayList();
	private String[] statesList = new String[50];
	//private ArrayList<String> statesList;
	private String buttonName;
	private ButtonListener actionForButton;
	private StatesPositions positionStore;
	private boolean added = false;

	/**
	 * Constructor method for Panel2.
	 * @param ripleyData
	 */
	public Panel2(RipleyData ripleyData){
		
		this.ripleyData = ripleyData;
		loadImages();
		addMapToThePanel();
	    this.setPreferredSize(new Dimension(800,800));
	    actionForButton= new ButtonListener(this);
	    positionStore = new StatesPositions();
	    
	    statesList = positionStore.getStatesNames();
	    
	}
	
	/**
	 * This method adds buttons to the map and positions them based on specific coordinates obtained from 
	 * the positions of states (StatesPositions).
	 */
	public void addButtons(){
		
		int maxFreq = ripleyData.getMaxFrequency();
		
		if(!added){
			for(int i = 0; i<50; i++){
		    	added = true;
		    	JButton tempButton = new JButton();
		    	mapImage.add(tempButton);
		    	insertAlien(tempButton, statesList[i], positionStore.stateXPos(statesList[i]), positionStore.stateYPos(statesList[i]), maxFreq);
		    }
		  }else{
			  mapImage.removeAll();
			for(int i = 0; i<50; i++){
			  JButton tempButton = new JButton();
		      mapImage.add(tempButton);
		      insertAlien(tempButton, statesList[i], positionStore.stateXPos(statesList[i]), positionStore.stateYPos(statesList[i]), maxFreq);
			}
		}
	}
	
	/**
	 * method that creates an object of SightingsWindow, passes name to its constructor to set the window's name
	 * @param name
	 */
	public void createSightingsWindow(String name){
		SightingsWindow w1= new SightingsWindow(name, ripleyData);
	}
	
	/**
	 * Loads images 'usanames' and 'alien' from Images folder in src.
	 * Creates a new ImageIcon, alienIcon from resulting alienimg.
	 */
	public void loadImages(){
		
		try {map = ImageIO.read(getClass().getResourceAsStream("/src/Images/usanames.png"));
		} catch (IOException e){}
		try {alienimg = ImageIO.read(getClass().getResourceAsStream("/src/Images/alien.png"));
		} catch (IOException e){}
		alienIcon = new ImageIcon(alienimg);
	}
	
	/**
	 * Adds mapImage to the panel. Also sets mapImage layout to null.
	 * 
	 */
	public void addMapToThePanel(){
		mapImage= new MyPanel(map);
		add(mapImage);
		mapImage.setPreferredSize(new Dimension(1000,1000));
		mapImage.setLayout(null);
		
	}
	
	/**
	 * The maximum frequency influences the size of the button.
	 * @param alien
	 * @param buttonName
	 * @param x
	 * @param y
	 * @param maxFrequency
	 */
	public void insertAlien(JButton alien, String buttonName, int x, int y, int maxFrequency){
	  
		int minLength = 20;
		int minWidth = 20;
		int maxDifference = 30;
		int calcwidth = 0;
		int calcheight = 0;
		
		int frequency=0;
		try{
			frequency = ripleyData.getStateFrequency(buttonName);
			
		} catch (Exception e){
			frequency = 0;
		}		
		
		calcwidth = minWidth + maxDifference*(frequency / maxFrequency);
		calcheight = minLength + maxDifference*(frequency/ maxFrequency);
		
		Image img = alienIcon.getImage();
		Image newimg = img.getScaledInstance(calcwidth, calcheight, java.awt.Image.SCALE_SMOOTH);
		alien.setIcon(new ImageIcon(newimg));
	   	alien.setBounds(x, y, calcwidth, calcheight);
	   	alien.setName(buttonName);
	   	
	   	if (frequency == 0){
	   		alien.setVisible(false);
	   	}else{
	   		alien.setVisible(true);
	   	}
	   	
	   	alien.addActionListener(actionForButton);

	}
	
	/**
	 * @return the name of a button, type String.
	 */
	public String getButtonName(){
		return buttonName;
	}
	
	
	
	/**
	 * 
	 * @author Rihem
	 * an inner class that extends JPanel and is used to add images to a panel as a background
	 */
	
		class MyPanel extends JPanel {

			BufferedImage img;

			public MyPanel(BufferedImage img) {
				this.img = img;
			}

			// Overriding paint component
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, this);
			}

		}	
}

