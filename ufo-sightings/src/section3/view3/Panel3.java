/**
 * 
 */
package section3.view3;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ripleytest.RipleyData;
import section3.controller3.StatisticsListener;
import section3.model3.NumberOfHoaxes;
import section3.model3.StatCalc;


/**
 * @author ardag
 * 
 * This class handles the display of section 3. Since our program follows an MVC structure, 
 * this class is the 'view' with respect to section 3 of our program.
 *
 */
public class Panel3 extends JPanel {


	private NumberOfHoaxes numberOfHoaxes;
	private ArrayList<JLabel> titleLabels = new ArrayList();
	private ArrayList<JLabel> statLabels = new ArrayList();
	
	private RipleyData ripleyData;
	private JPanel[] panels = new JPanel[4];
	//private JPanel panel1;
	//private JPanel panel2;
	//private JPanel panel3;
	//private JPanel panel4;
	private StatCalc statCalc;
	//private int panel1stat;
	//private int panel2stat;
	//private int panel3stat;
	//private int panel4stat;
 
	private int[] selections = new int[4];
	private StatisticsListener listener;
	
	public Panel3(RipleyData ripleyData) {
		
		this.ripleyData = ripleyData;
		statCalc = new StatCalc(ripleyData);
		design();
	}
	
	/**
	 * Creates Panel3 interface, which has multiple 'left' and 'right' JButtons
	 * and appropriate statistics labels.
	 */
	public void design() {

		this.setLayout(new GridLayout(2, 2));

		panels[0] = new JPanel();
		panels[0].setLayout(new BorderLayout());
		panels[1] = new JPanel();
		panels[1].setLayout(new BorderLayout());
		panels[2] = new JPanel();
		panels[2].setLayout(new BorderLayout());
		panels[3] = new JPanel();
		panels[3].setLayout(new BorderLayout());

		listener = new StatisticsListener(this);
		
		JButton rightClick = new JButton(">");
		rightClick.setName("rightClick");
		rightClick.addActionListener(listener);
		panels[0].add(rightClick, BorderLayout.EAST);
		
		JButton leftClick = new JButton("<");
		leftClick.setName("leftClick");
		leftClick.addActionListener(listener);
		panels[0].add(leftClick, BorderLayout.WEST);
		
		JButton rightClick2 = new JButton(">");
		rightClick2.setName("rightClick2");
		rightClick2.addActionListener(listener);
		panels[1].add(rightClick2, BorderLayout.EAST);
		
		JButton leftClick2 = new JButton("<");
		leftClick2.setName("leftClick2");
		leftClick2.addActionListener(listener);
		panels[1].add(leftClick2, BorderLayout.WEST);
		
		JButton rightClick3 = new JButton(">");
		rightClick3.setName("rightClick3");
		rightClick3.addActionListener(listener);
		panels[2].add(rightClick3, BorderLayout.EAST);
		
		JButton leftClick3 = new JButton("<");
		leftClick3.setName("leftClick3");
		leftClick3.addActionListener(listener);
		panels[2].add(leftClick3, BorderLayout.WEST);
		
		JButton rightClick4 = new JButton(">");
		rightClick4.setName("rightClick4");
		rightClick4.addActionListener(listener);
		panels[3].add(rightClick4, BorderLayout.EAST);
		
		JButton leftClick4 = new JButton("<");
		leftClick4.setName("leftClick4");
		leftClick4.addActionListener(listener);
		panels[3].add(leftClick4, BorderLayout.WEST);
		
		
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>Number of Hoaxes</html>"));
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>Number of Non U.S. Sightings</html>"));
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>State with Most Sightings</html>"));
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>Number of Sightings on Other Platforms</html>"));
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>Most Likely Hour to See a Sighting</html>"));
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>Total Duration of All Incidents</html>"));
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>Average Time Between Sightings</html>"));
		titleLabels.add(new JLabel("<html><div style='text-align: center;'>Number of Potentially Unclear Sightings</html>"));
		titleLabels.get(0).setHorizontalAlignment(JLabel.CENTER);
		titleLabels.get(1).setHorizontalAlignment(JLabel.CENTER);
		titleLabels.get(2).setHorizontalAlignment(JLabel.CENTER);
		titleLabels.get(3).setHorizontalAlignment(JLabel.CENTER);
		titleLabels.get(4).setHorizontalAlignment(JLabel.CENTER);
		titleLabels.get(5).setHorizontalAlignment(JLabel.CENTER);
		titleLabels.get(6).setHorizontalAlignment(JLabel.CENTER);
		titleLabels.get(7).setHorizontalAlignment(JLabel.CENTER);
		
		statLabels.add(new JLabel("Test"));
		statLabels.add(new JLabel("Test"));
		statLabels.add(new JLabel("Test"));
		statLabels.add(new JLabel("Test"));
		statLabels.add(new JLabel("Test"));
		statLabels.add(new JLabel("Test"));
		statLabels.add(new JLabel("Test"));
		statLabels.add(new JLabel("Test"));
			
		this.add(panels[0]);
		this.add(panels[1]);
		this.add(panels[2]);
		this.add(panels[3]);
		
		loadSelections();
		saveSelections();
		setStatistics();
		
	}
	
	/**
	 * Sets initial statistics labels.
	 * @param selections
	 * 
	 */
	public void setStatistics(){
				
		panels[0].add(titleLabels.get(selections[0]), BorderLayout.NORTH);
		panels[1].add(titleLabels.get(selections[1]), BorderLayout.NORTH);
		panels[2].add(titleLabels.get(selections[2]), BorderLayout.NORTH);
		panels[3].add(titleLabels.get(selections[3]), BorderLayout.NORTH);
		
		panels[0].add(statLabels.get(selections[0]), BorderLayout.CENTER);
		panels[1].add(statLabels.get(selections[1]), BorderLayout.CENTER);
		panels[2].add(statLabels.get(selections[2]), BorderLayout.CENTER);
		panels[3].add(statLabels.get(selections[3]), BorderLayout.CENTER);
		
	}

	/**
	 * Updates statLabels.
	 */
	public void updateStatistics(){
		
		statLabels.get(0).setText(statCalc.getStat(1));
		statLabels.get(1).setText(statCalc.getStat(2));
		statLabels.get(2).setText(statCalc.getStat(3));
		statLabels.get(3).setText(statCalc.getStat(4));
		statLabels.get(4).setText(statCalc.getStat(5));
		statLabels.get(5).setText(statCalc.getStat(6));
		statLabels.get(6).setText(statCalc.getStat(7));
		statLabels.get(7).setText(statCalc.getStat(8));
		statLabels.get(0).setHorizontalAlignment(JLabel.CENTER);
		statLabels.get(1).setHorizontalAlignment(JLabel.CENTER);
		statLabels.get(2).setHorizontalAlignment(JLabel.CENTER);
		statLabels.get(3).setHorizontalAlignment(JLabel.CENTER);
		statLabels.get(4).setHorizontalAlignment(JLabel.CENTER);
		statLabels.get(5).setHorizontalAlignment(JLabel.CENTER);
		statLabels.get(6).setHorizontalAlignment(JLabel.CENTER);
		statLabels.get(7).setHorizontalAlignment(JLabel.CENTER);
		repaint();
	}
	
	/**
	 * Loads selections from "userpreferences.ufo" file, stores in selections.
	 */
	public void loadSelections(){
		
		try{
			FileInputStream fin= new FileInputStream ("userpreferences.ufo");
			ObjectInputStream ois = new ObjectInputStream(fin);
			selections = (int[])ois.readObject();
			fin.close();
			} catch(Exception e){ 
				selections[0] = 0;
				selections[1] = 1;
				selections[2] = 2;
				selections[3] = 3;
			}	
	}
	
	/**
	 * Save selections on "userpreferences.ufo".
	 */
	public void saveSelections(){
		try{
			FileOutputStream fout = new FileOutputStream ("userpreferences.ufo");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(selections);
			fout.close();
			} catch(Exception e){}
	}
	
	/**
	 * Method which allows statistics viewed on screen to be dynamically changed.
	 * There are a total of 8 stats to choose from.
	 * @param statToBeIncreased
	 * @param amount
	 */
	public void changeStat(int statToBeIncreased, int amount){
		
		panels[statToBeIncreased].remove(titleLabels.get(selections[statToBeIncreased]));
		panels[statToBeIncreased].remove(statLabels.get(selections[statToBeIncreased]));
		
		selections[statToBeIncreased] += amount;
		if (selections[statToBeIncreased] == 8){
			selections[statToBeIncreased] = 0;
		}if(selections[statToBeIncreased] == -1){
			selections[statToBeIncreased] = 7;
		}
		setStatistics();
		panels[statToBeIncreased].repaint();
		int counter = 0;
		for(int x = 0 ; x < 4; x++){
			if(selections[statToBeIncreased] == selections[x]){
				counter++;
			}
		}
		if (counter == 2){
			changeStat(statToBeIncreased, amount);	
		}
		
		saveSelections();
	}
}
