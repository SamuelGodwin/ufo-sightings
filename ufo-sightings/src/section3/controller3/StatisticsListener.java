/**
 * 
 */
package section3.controller3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import ripleytest.RipleyData;
import section3.model3.AverageTimeBetweenSightings;
import section3.model3.MostLikely;
import section3.model3.Non_US_Sightings;
import section3.model3.NumberOfHoaxes;
import section3.model3.SightingsViaGoogle;
import section3.model3.StatCalc;
import section3.model3.TotalDurationOfIncidents;
import section3.view3.Panel3;

/**
 * @author Samuel
 * This class forms section 3's listener (MVC).
 * This handles interactions between section 3's model and its view; Panel3.
 */

public class StatisticsListener implements ActionListener {

	
	private Panel3 panel3;
		
	public StatisticsListener(Panel3 panel3) {
		
		this.panel3 = panel3;
		
	}
	
	public void actionPerformed(ActionEvent e){
		
		JButton button = (JButton) e.getSource();
			
		if(button.getName().equals("leftClick")){
			panel3.changeStat(0, -1);
			panel3.setStatistics();
			panel3.updateStatistics();
			
			
		}
			
		else if(button.getName().equals("rightClick")){
			panel3.changeStat(0, 1);
			panel3.setStatistics();
			panel3.updateStatistics();
		}
			
		else if(button.getName().equals("leftClick2")){
			panel3.changeStat(1, -1);
			panel3.setStatistics();
			panel3.updateStatistics();
		}

		else if(button.getName().equals("rightClick2")){
			panel3.changeStat(1, 1);
			panel3.setStatistics();
			panel3.updateStatistics();
		}

		else if(button.getName().equals("leftClick3")){
			panel3.changeStat(2, -1);
			panel3.setStatistics();
			panel3.updateStatistics();
		}

		else if(button.getName().equals("rightClick3")){
			panel3.changeStat(2, 1);
			panel3.setStatistics();
			panel3.updateStatistics();
		}
			
		else if(button.getName().equals("leftClick4")){
			panel3.changeStat(3, -1);
			panel3.setStatistics();
			panel3.updateStatistics();
		}

		else if(button.getName().equals("rightClick4")){
			panel3.changeStat(3, 1);
			panel3.setStatistics();
			panel3.updateStatistics();
		}
		
	}
		
}
