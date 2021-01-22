/**
 * 
 */
package section4;

import javax.swing.JLabel;

/**
 * This class is used to create repeatable instances of an obstacle (either a Cow or Tractor).
 * It will store data relavant to its coordinates, position, deathstate as well as JLabel data.
 */
public class cow extends JLabel {

	private float x;
	private float y;
	private float x1;
	private float y1;
	private int position;
	private boolean alive = true;
	private boolean anim1 = true;;
	private boolean anim2 = false;

	/**
	 * Sets destination coordinates used to modulate speed and
	 * direction when movement is required
	 * 
	 * @param x1
	 * @param y1
	 */
	public void setDestination(float x1, float y1) {
		this.x1 = x1;
		this.y1 = y1;
	}

	/**
	 * Method to set field which determines which part of death animation
	 * is required at any given instance
	 * 
	 * @param anim1
	 */
	public void setanim1(boolean anim1) {
		this.anim1 = anim1;
	}

	/**
	 * Method to set field which determines which part of death animation
	 * is required at any given instance
	 * 
	 * @param anim2
	 */
	public void setanim2(boolean anim2) {
		this.anim2 = anim2;
	}

	/**
	 * Getter for anim1 flag
	 * 
	 * @return
	 */
	public boolean anim1() {
		return anim1;
	}

	/**
	 * Getter for anim2 flag
	 * 
	 * @return
	 */
	public boolean anim2() {
		return anim2;
	}

	/**
	 * Getter for alive tag
	 * 
	 * @return
	 */
	public boolean alive() {
		return alive;
	}

	/**
	 * Setter for alive tag
	 * 
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * Setter for position
	 * 
	 * @param position
	 */
	public void setPos(int position) {
		this.position = position;
	}

	/**
	 * Getter for position
	 * 
	 * @return
	 */
	public int getPos() {
		return position;
	}

	/**
	 * Setter for X coordinate
	 * 
	 * @param x
	 */
	public void setx(float x) {
		this.x = x;
	}

	/**
	 * Setter for Y coordinate
	 * 
	 * @param y
	 */
	public void sety(float y) {
		this.y = y;
	}

	/**
	 * Setter for x and y coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public void setCoords(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for X coordinate
	 * 
	 * @return
	 */
	public float getx() {
		return x;
	}

	/**
	 * Getter for X destination
	 * 
	 * @return
	 */
	public float getDestinationX() {
		return x1;
	}

	/**
	 * Getter for Y destination
	 * 
	 * @return
	 */
	public float getDestinationY() {
		return y1;
	}

	/**
	 * Getter for Y coordinate
	 * 
	 * @return
	 */
	public float gety() {
		return y;
	}
}
