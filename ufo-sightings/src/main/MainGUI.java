/**
 * 
 */
package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ripleytest.RipleyData;
import section1.view1.Panel1;

import section2.view2.Panel2;

import section3.view3.Panel3;
import section4.Panel4;

/**
 * This is the main GUI of our program. This class creates an object of
 * RipleyData which will be passed to other classes in the
 * program, ultimately minimising calls to Ripley's API.
 * 
 * @author Samuel & Sean
 *
 */
public class MainGUI {

	private JFrame window = new JFrame("UFO sightings application");
	private JPanel topBar; // topmost bar of application, holds date range drop
							// down menus
	private JPanel contentPane; // panel that holds each of our JPanels, i.e.
								// the content
	private JPanel bottomBar; // bottommost bar of application, holds buttons
								// and textarea
	private JLabel firstDateLabel;
	private JLabel lastDateLabel;
	private JComboBox jcbFirstDate;
	private JComboBox jcbLastDate;

	private JButton leftButton;
	private JButton rightButton;
	private JLabel bottomText;

	private JButton refreshButton;

	// Testing populating data
	// private LikeliestState likeliestState = new LikeliestState();

	private ArrayList<JPanel> ContentPanels;
	private int panelIndex = 0;
	private MainButtonController buttonController;
	private Panel1 firstPanel;
	private Panel3 thirdPanel;
	private Panel2 secondPanel;
	private Panel4 fourthPanel;
	private jcbListener comboBoxController;
	private RipleyData ripleyData;

	/**
	 * @return JComboBox jcbFirstDate
	 */
	public JComboBox getFirstDateBox() {
		return jcbFirstDate;
	}

	/**
	 * @return JComboBox jcbLastDate
	 */
	public JComboBox getLastDateBox() {
		return jcbLastDate;
	}

	// An object of RipleyData for testing
	// private RipleyData ripleyData;

	/**
	 * Constructor method for MainGUI. Creates an object of RipleyData.
	 * This method also sets up MainGUI's user interface.
	 * 
	 */
	public MainGUI() {

		RipleyData ripleyData = new RipleyData();

		firstPanel = new Panel1(ripleyData);
		thirdPanel = new Panel3(ripleyData);
		secondPanel = new Panel2(ripleyData);
		fourthPanel = new Panel4(this);

		ContentPanels = new ArrayList();

		ContentPanels.add(firstPanel);
		ContentPanels.add(secondPanel);
		ContentPanels.add(thirdPanel);
		ContentPanels.add(fourthPanel);

		// window = new JFrame("UFO sightings application");
		window.setSize(1000, 800);
		window.setPreferredSize(new Dimension(1000, 800));
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		topBar = new JPanel();
		topBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		window.add(topBar, BorderLayout.NORTH);

		firstDateLabel = new JLabel("Initial date:");
		topBar.add(firstDateLabel);

		// Populating JComboBox
		jcbFirstDate = new JComboBox(ripleyData.getYearsArray());
		// Populating JComboBox
		jcbLastDate = new JComboBox(ripleyData.getYearsArray());

		comboBoxController = new jcbListener(this);
		// Add action listener for JComboBox1
		jcbFirstDate.addActionListener(comboBoxController);
		// Add action listener for JComboBox2
		jcbLastDate.addActionListener(comboBoxController);

		// Inner class for now, will decide if appropriate later based on
		// frequency of use, etc.

		topBar.add(jcbFirstDate);
		lastDateLabel = new JLabel("Last date:");
		topBar.add(lastDateLabel);

		topBar.add(jcbLastDate);

		refreshButton = new JButton("Fetch!");
		topBar.add(refreshButton);

		buttonController = new MainButtonController(this, ripleyData, secondPanel, thirdPanel);
		refreshButton.setName("refresh");
		refreshButton.addActionListener(buttonController);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		window.add(contentPane, BorderLayout.CENTER);

		bottomBar = new JPanel();
		bottomBar.setLayout(new BorderLayout());
		window.add(bottomBar, BorderLayout.SOUTH);

		// The "<" and ">" buttons need to be disabled on program start until
		// the program successfully fetches Ripley data from within date range
		// (Panel1).
		leftButton = new JButton("<");
		bottomBar.add(leftButton, BorderLayout.WEST);
		// Disable left button
		leftButton.setEnabled(false);

		// Will ripleyData.getLastUpdated() need calling at other times in our
		// program? (I am not sure).
		bottomText = new JLabel(ripleyData.getLastUpdatedInfo(), SwingConstants.CENTER);
		bottomBar.add(bottomText, BorderLayout.CENTER);

		rightButton = new JButton(">");
		bottomBar.add(rightButton, BorderLayout.EAST);
		// Disable right button
		rightButton.setEnabled(false);

		firstPanel.addInitialYearText(jcbFirstDate.getSelectedItem(), jcbLastDate.getSelectedItem());
		contentPane.add(firstPanel);

		rightButton.setName("right");
		rightButton.addActionListener(buttonController);
		leftButton.setName("left");
		leftButton.addActionListener(buttonController);

		window.pack();
		window.setVisible(true);

	}

	/**
	 * 
	 */
	public void right() {
		if (panelIndex < ContentPanels.size() - 1) {
			contentPane.remove(ContentPanels.get(panelIndex));
			panelIndex++;
			contentPane.repaint();
			contentPane.add(ContentPanels.get(panelIndex));
			contentPane.repaint();
			window.setVisible(true);
		}

		if (panelIndex == 3) {
			fourthPanel.switchTo();
		}

	}

	/**
	 * 
	 */
	public void left() {

		if (panelIndex == 3) {
			setResizeable(true);
			fourthPanel.checkClear();
		}

		if (panelIndex > 0) {
			contentPane.remove(ContentPanels.get(panelIndex));
			panelIndex--;
			contentPane.repaint();
			contentPane.add(ContentPanels.get(panelIndex));
			contentPane.repaint();
			window.setVisible(true);
		}
	}

	/**
	 * Determines whether a date range selected by a user is valid, and what
	 * will be displayed in Panel1's JLabel accordingly.
	 */
	public void jcbbutton() {
		if (jcbFirstDate.getSelectedItem() != "-" && jcbLastDate.getSelectedItem() != "-") {
			if (Integer.parseInt((String) jcbFirstDate.getSelectedItem()) < Integer
					.parseInt((String) jcbLastDate.getSelectedItem())) {
				firstPanel.setDatesText(jcbFirstDate.getSelectedItem(), jcbLastDate.getSelectedItem());
			} else {
				firstPanel.invalidDate();

			}

		}
	}

	/**
	 * Sets size of JFrame window, based on passed parameters x and y.
	 * 
	 * @param x
	 * @param y
	 */
	public void setSize(int x, int y) {
		window.setSize(x, y);
	}

	/**
	 * Sets whether or not JFrame window is resizable, based on boolean passed
	 * parameter x.
	 * 
	 * @param x
	 * @param y
	 */
	public void setResizeable(boolean x) {
		window.setResizable(x);
	}

	// More testing...
	public Object getFirstSelectedItem() {
		return jcbFirstDate.getSelectedItem();
	}

	// More testing...
	public Object getLastSelectedItem() {
		return jcbLastDate.getSelectedItem();
	}

	/**
	 * This is an accessor method for firstPanel.
	 * 
	 * @return firstPanel, an instance of Panel1 which extends JPanel.
	 */
	public Panel1 getPanel1() {
		return firstPanel;
	}

	/**
	 * Sets whether or not leftButton and rightButton are enabled, based on
	 * boolean passed parameter bool.
	 * 
	 * @param bool
	 */
	public void setButtons(Boolean bool) {
		leftButton.setEnabled(bool);
		rightButton.setEnabled(bool);
	}

}
