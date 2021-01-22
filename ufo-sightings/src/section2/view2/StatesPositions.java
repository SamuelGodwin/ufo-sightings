/**
 * 
 */
package section2.view2;

import java.util.TreeMap;

/**
 * 
 * @author Ghighou
 *
 * A class that stores every state's position i.e. stores its coordinates x, y on the map
 *
 */
public class StatesPositions {
	
	private TreeMap<String, Integer> stateXPos;
	private TreeMap<String, Integer> stateYPos;
	private String[] statesArray;
	
	
	
	/**
	 * Constructor initializes the treemaps used to map each state to its coordinate
	 */
	public StatesPositions(){
		this.stateXPos= new TreeMap<String, Integer>();
		this.stateYPos= new TreeMap<String, Integer>();
		this.statesArray= new String[50];
		setStateXPos();
		setStateYPos();
	}
	
	/**
	 * A method mapping each state to its x coordinate
	 */
	public void setStateXPos(){
		stateXPos.put("AK", 50);
		stateXPos.put("AL", 678);
		stateXPos.put("AR", 585);
		stateXPos.put("AZ", 300);
		stateXPos.put("CA", 191);
		stateXPos.put("CO", 410);
		stateXPos.put("CT", 900);
		stateXPos.put("DE", 850);
		stateXPos.put("FL", 760);
		stateXPos.put("GA", 720);
		stateXPos.put("HI", 264);
		stateXPos.put("IA", 560);
		stateXPos.put("ID", 312);
		stateXPos.put("IL", 638);
		stateXPos.put("IN", 685);
		stateXPos.put("KS", 526);
		stateXPos.put("KY", 715);
		stateXPos.put("LA", 594);
		stateXPos.put("MA", 927);
		stateXPos.put("MD", 853);
		stateXPos.put("ME", 950);
		stateXPos.put("MI", 708);
		stateXPos.put("MN", 555);
		stateXPos.put("MO", 580);
		stateXPos.put("MS", 634);
		stateXPos.put("MT", 300);
		stateXPos.put("NC", 785);
		stateXPos.put("ND", 444);
		stateXPos.put("NE", 530);
		stateXPos.put("NH", 910);
		stateXPos.put("NJ", 861);
		stateXPos.put("NM", 375);
		stateXPos.put("NV", 250);
		stateXPos.put("NY", 870);
		stateXPos.put("OH", 729);
		stateXPos.put("OK", 520);
		stateXPos.put("OR", 219);
		stateXPos.put("PA", 800);
		stateXPos.put("RI", 904);
		stateXPos.put("SC", 760);
		stateXPos.put("SD", 530);
		stateXPos.put("TN", 700);
		stateXPos.put("TX", 526);
		stateXPos.put("UT", 300);
		stateXPos.put("VA", 809);
		stateXPos.put("VT", 895);
		stateXPos.put("WA", 220);
		stateXPos.put("WI", 630);
		stateXPos.put("WV", 243);
		stateXPos.put("WY", 410);
		
	}
	
	/**
	 * A method mapping each state to its y coordinate
	 */
	public void setStateYPos(){
		stateYPos.put("AK", 197);
		stateYPos.put("AL", 370);
		stateYPos.put("AR", 335);
		stateYPos.put("AZ", 310);
		stateYPos.put("CA", 287);
		stateYPos.put("CO", 229);
		stateYPos.put("CT", 209);
		stateYPos.put("DE", 250);
		stateYPos.put("FL", 495);
		stateYPos.put("GA", 370);
		stateYPos.put("HI", 485);
		stateYPos.put("IA", 158);
		stateYPos.put("ID", 136);
		stateYPos.put("IL", 225);
		stateYPos.put("IN", 213);
		stateYPos.put("KS", 240);
		stateYPos.put("KY", 267);
		stateYPos.put("LA", 395);
		stateYPos.put("MA", 192);
		stateYPos.put("MD", 280);
		stateYPos.put("ME", 88);
		stateYPos.put("MI", 124);
		stateYPos.put("MN", 90);
		stateYPos.put("MO", 250);
		stateYPos.put("MS", 379);
		stateYPos.put("MT", 50);
		stateYPos.put("NC", 310);
		stateYPos.put("ND", 20);
		stateYPos.put("NE", 197);
		stateYPos.put("NH", 139);
		stateYPos.put("NJ", 223);
		stateYPos.put("NM", 380);
		stateYPos.put("NV", 270);
		stateYPos.put("NY", 128);
		stateYPos.put("OH", 205);
		stateYPos.put("OK", 310);
		stateYPos.put("OR", 142);
		stateYPos.put("PA", 200);
		stateYPos.put("RI", 173);
		stateYPos.put("SC", 350);
		stateYPos.put("SD", 134);
		stateYPos.put("TN", 310);
		stateYPos.put("TX", 400);
		stateYPos.put("UT", 210);
		stateYPos.put("VA", 265);
		stateYPos.put("VT", 120);
		stateYPos.put("WA", 35);
		stateYPos.put("WI", 120);
		stateYPos.put("WV", 764);
		stateYPos.put("WY", 161);
		
	}
	

	/**
	 * a getter for the state's x coordinate
	 * @param state
	 * @return value for key state
	 */
	public int stateXPos(String state){
		
		return stateXPos.get(state);
	}
	
	/**
	 * a getter for the state's y coordinate
	 * @param state
	 * @return value for key state
	 */
	public int stateYPos(String state){
		return stateYPos.get(state);
		}
		
	/**
	 * a getter for the state's xPos treeMap
	 * @return stateXPos
	 */
	public TreeMap<String, Integer> xPosMap(){
		return stateXPos;
	}
	
	/**
	 * a getter for the state's yPos treeMap
	 * @return stateYPos
	 */
		public TreeMap<String, Integer> yPosMap(){
			return stateYPos;
		}
		
	/**
	 * store the names from stateXPos treemap in statesArray
	 */
	public void storeStateNames(){	
		String firstKey= "AK";
		for(int i=0; i<50; i++) {
			statesArray[i]= firstKey;
			firstKey=stateYPos.higherKey(firstKey);		
		}
		
		
	}
	
	/**
	 * a getter for statesArray
	 * @return statesArray
	 */
	public String[] getStatesNames(){
		storeStateNames();
		return statesArray;
		
	}
	
	
	                          
		

}

