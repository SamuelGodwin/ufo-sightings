/**
 * 
 */
package section1.view1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import api.ripley.Ripley;
import main.MainGUI;
import ripleytest.RipleyData;

/**
 *  
 * This class models the runway in our solution. It has 2 fields; length
 * and width. This class and its methods are public, so are accessible anywhere.
 *
 * 
 * @author Samuel
 *
 */
public class Panel1 extends JPanel {

	private JScrollPane firstPanelScroll;


	private JLabel label;
	private JLabel label2;
	private String text;
	private String ripleyVer;
	private String yearsSelected;
	private RipleyData ripleyData;
	private JPanel containsLabels;

	/**
	 * Constructor method for Panel1. Sets ripleyData field of this class as a
	 * reference to ripleyData parameter.
	 * 
	 * Finally, sets up Panel1 GUI.
	 * 
	 * @param ripleyData
	 */
	public Panel1(RipleyData ripleyData) {	
		
		this.ripleyData = ripleyData;
		ripleyVer = "Welcome to the Ripley API version " + ripleyData.getRipleyVersion();
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel firstPanelCenter = new JPanel();
		firstPanelCenter.setLayout(new GridLayout(1,1)); 
						
		this.add(firstPanelCenter, BorderLayout.CENTER);
		
		
		JPanel containsLabelContainer = new JPanel();
		containsLabelContainer.setLayout(new GridBagLayout()); 
		containsLabels = new JPanel();
		containsLabels.setLayout(new BoxLayout(containsLabels, BoxLayout.Y_AXIS));
		text = ripleyVer
				+ "<br>\n\nPlease select from the dates above, \n in order to<br>begin analysing UFO sighting data."
				+ "<br><br>";
		label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		label2 = new JLabel("<html><div style='text-align: center;'><br>Please now interact with this data using the<br>buttons to the left and the right.</div></html>");
		Font font = label.getFont();
		label.setFont(font.deriveFont(font.getStyle() ^ Font.BOLD));
		containsLabels.add(label);
		
		containsLabelContainer.add(containsLabels);
		
		// 'firstPanelScroll' contains 'containsLabelContainer'.
		firstPanelScroll = new JScrollPane(containsLabelContainer);
		
		// 'firstPanelCenter' contains 'firstsPanelScroll'.
		firstPanelCenter.add(firstPanelScroll);

		label.setHorizontalAlignment(JLabel.CENTER);
		label2.setHorizontalAlignment(JLabel.CENTER);
		
		this.setVisible(true);

	}

	/**
	 * 
	 * Called upon a user making a JComboBox selection. Sets 'yearsSelected' field to hold the
	 * JComboBox value selected (parameters).
	 * 
	 * @param year1
	 * @param year2
	 */
	public void addInitialYearText(Object year1, Object year2) {
		yearsSelected = "\nDate range selected, " + year1 + "-" + year2;
	}

	/**
	 * This method confirms to the user the JComboBox years they have selected by updating the
	 * JLabel on-screen.
	 * 
	 * @param year1
	 * @param year2
	 */
	public void setDatesText(Object year1, Object year2) {

		yearsSelected = "\nDate range selected, " + year1 + "-" + year2;

		text = ripleyVer
				+ "<br>\n\nPlease select from the dates above, \n in order to<br>begin analysing UFO sighting data."
				+ "<br><br>" + yearsSelected;

		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");

	}

	/**
	 * This method is called only if user chooses a date range which is invalid.
	 * Sets JLabel to inform user accordingly.
	 */
	public void invalidDate() {
		yearsSelected = "(Please enter a valid date range).";

		text = ripleyVer
				+ "<br>\n\nPlease select from the dates above, \n in order to<br>begin analysing UFO sighting data."
				+ "<br><br>" + yearsSelected;

		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
	}

	/**
	 * This method is called after 'storeIncidentsInRange' in RipleyData.
	 * Sets JLabel to inform the user accordingly (via 'updateJLabelText') & adds label2. 
	 * At this point the left and right JButtons in MainGUI are also enabled.
	 */
	public void fetchSuccessful() {
		// Updates JLabel with fetch time duration.
		fetchCompleteJLabelText();

		// Add the bold label next
		containsLabels.add(label2);
		label.setVerticalAlignment(JLabel.CENTER); // WORKS (LABEL)
		label2.setHorizontalAlignment(JLabel.CENTER); // WORKS (LABEL2)

	}

	// Could this cover multiple cases?
	/**
	 * This is the update to the JLabel which is made after Ripley data is fetched.
	 * It appends the 'getDurationString' field to the existing text, which it then displays to the user.
	 */
	private void fetchCompleteJLabelText() {
		text = ripleyVer
				+ "<br>\n\nPlease select from the dates above, \n in order to<br>begin analysing UFO sighting data."
				+ "<br><br>" + yearsSelected + "<br><br>Grabbing data..." + "<br>" + "<br>"
				+ ripleyData.getDurationString();

		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
	}

	/**
	 * This method is called as user clicks 'refresh' in MainGUI.
	 * Its purpose is to display a message or gif while the user waits as the
	 * program fetches Ripley data. The method is called just before data is
	 * fetched via 'timeRipleyFetch' in RipleyData. It updates JLabels informing user of fetch.
	 * 
	 * @param year1
	 * @param year2
	 */
	public void setGifAndFetchText(Object year1, Object year2) {

		yearsSelected = "\nDate range selected, " + year1 + "-" + year2 + ".";

		text = ripleyVer
				+ "<br>\n\nPlease select from the dates above, \n in order to<br>begin analysing UFO sighting data."
				+ "<br><br>" + yearsSelected + "<br><br>Grabbing data..." + "<br>" + "<br>";

		label.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
		System.out.println("Grabbing data...");
	}
}
