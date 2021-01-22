/**
 * 
 */
package main;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;

import ripleytest.RipleyData;
import section1.view1.Panel1;
import section2.view2.Panel2;
import section3.model3.MostLikely;
import section3.view3.Panel3;

/**
 * @author Sean
 *
 */
public class MainButtonController implements ActionListener {

	private MainGUI maingui;
	private long fetchDuration;
	private RipleyData ripleyData;
	private Panel1 firstPanel;
	
	private MostLikely mostLikely;
	private Panel2 panel2;
	private Panel3 panel3;
	
	/**
	 * Constructor method for MainButtonController. Sets fields in this class
	 * to references of objects passed as parameters. Also, instantiates 'MostLikely'.
	 * This class is passed a copy of ripleyData, through which to access stored data.
	 * @param maingui
	 * @param ripleyData
	 * @param panel2
	 * @param panel3
	 */
	public MainButtonController(MainGUI maingui, RipleyData ripleyData, Panel2 panel2, Panel3 panel3){
		this.maingui = maingui;
		this.ripleyData = ripleyData;
		this.panel2 = panel2;
		firstPanel = maingui.getPanel1();
		this.panel3 = panel3;
		mostLikely = new MostLikely(ripleyData);
	}
	
	/**
	 * Below are the events which take place when the refresh/fetch button in 
	 * MainGUI is pressed. This is where the fetching of Ripley data is handled.
	 */
	public void actionPerformed(ActionEvent e){
		
		JButton button = (JButton) e.getSource();
		if(button.getName().equals("refresh")){
			
			Object firstJComboYear = maingui.getFirstSelectedItem();
			Object secondJComboYear = maingui.getLastSelectedItem();
					
			// GETTERS OF THE BELOW.
			if (firstJComboYear != "-" && secondJComboYear != "-") {

				if (Integer.parseInt((String) firstJComboYear) < Integer.parseInt((String) secondJComboYear)) {
					
					// INSERT METHOD HERE FROM FIRSTPANEL THAT SETS THE GIF AND LOADING TEXT
					
					// clears all collections, in case this is a re-fetch
					ripleyData.clearCollections();
					firstPanel.setGifAndFetchText(firstJComboYear, secondJComboYear);
					
					// fetchRipleyData
					ripleyData.timeRipleyFetch(firstJComboYear, secondJComboYear);
					
					// Data was accessed successfully so update display.....
					firstPanel.fetchSuccessful();
					
					
					// TESTING CLASS FILE CREATION
					ArrayList<String> ripleyYears = ripleyData.getRipleyYears();
					
					
					
					ripleyData.fillLocations();
					ripleyData.fillTimes();
					ripleyData.fillHours();
					ripleyData.fillHourFrequencies();
					ripleyData.setAverageTimeBetweenSightings();
					
					// Changes left and right buttons to CLICKABLE.
			        maingui.setButtons(true);
			        panel2.addButtons();
			        panel3.updateStatistics();
			        

				} else {
					firstPanel.invalidDate();
				}
			}
			
			

		}else if(button.getName().equals("right")){
			maingui.right();
		}else if(button.getName().equals("left")){
			maingui.left();
		}
		

		
	}
	
	
}
