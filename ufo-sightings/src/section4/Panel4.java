/**
 * 
 */
package section4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import main.MainGUI;
import ripleytest.RipleyData;

/**
 * This class is used to run a game of 'Alien Run 4000'.
 * It contains methods to set up an introduction screen and a game,
 * logic to determine the gamestate, draw gamestate.
 */
public class Panel4 extends JPanel {

	private MainGUI gui;
	private ImageIcon spriteimg;
	private JLabel sprite;
	private BufferedImage splash1img;
	private JLabel splash1;

	private boolean splash;
	private BufferedImage splash2img;
	private BufferedImage splash3img;
	private JLabel splash2;
	private JLabel splash3;
	private JLabel instructions;
	private JLayeredPane gameboard;
	private JButton startButton;
	private StartButtonController startButtonController;
	private boolean gameRunning = false;
	private BufferedImage backgroundimg;
	private JLabel background1;
	private float backX = 0;
	private float backY = -620;
	private float backX2 = 900;
	private float backY2 = -100;
	private JLabel background2;
	private boolean back1 = true;
	private boolean back2 = false;
	private JLabel scorelabel;
	private int lives;
	private int playerpos;
	private BufferedImage ufoimg;
	private BufferedImage beamimg;
	private JLabel ufo;
	private JLabel beam;
	private boolean gameover;
	private boolean anim1;
	private boolean anim2;
	private float X;
	private float Y;
	private long delta;
	private BufferedImage jumpimg;
	private boolean jump;
	private long jumpTimer;
	private int tempY;
	private ArrayList<cow> cows;
	private ArrayList<cow> tractors;
	private long objectCounter;
	private BufferedImage cowimg;
	private BufferedImage tractorimg;
	private Random rand;
	private BufferedImage cowdeathimg;
	private long flickercounter;
	private BufferedImage beamimg2;
	private boolean collided = false;
	private BufferedImage damageimg;
	private long collisioncounter;
	public int highScore;
	private ImageIcon alienimg;
	private JLabel highScoreLabel;
	private JLabel titleLabel;
	private BufferedImage titleimg;
	private JLabel controls;
	private boolean paused = false;
	private JLabel pausedlabel;
	private long counter;
	private ImageIcon ufogif;

	/**
	 * Constructor - Calls initial methods to load resources, set relevant fields
	 * @param gui
	 */
	public Panel4(MainGUI gui) {

		this.gui = gui;

		this.setSize(900, 720);
		this.setLayout(new BorderLayout());
		splash = false;
		loadimages();
		iconsetup();
		gameboard = new JLayeredPane();
		gameboard.setLayout(null);
		add(gameboard);
		rand = new Random();
		load();
		gameboard.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (!jump && !collided && !paused) {
						playerpos--;
						if (playerpos < 1)
							playerpos = 1;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (!jump && !collided && !paused) {
						playerpos++;
						if (playerpos > 3)
							playerpos = 3;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!collided && !paused)
						jump = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (!collided)
						jump = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_P) {
					togglePause();
				}
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					lives = 3;
					counter = 0;
					playerpos = 2;
				}
			}
		});

	}

	/**
	 * Loads resources - loads images into BufferedImage and gifs into Icon
	 * fields to be used throughout the game
	 */
	private void loadimages() {
		try {
			splash1img = ImageIO.read(getClass().getResourceAsStream("/src/spash1edit.png"));
		} catch (IOException e) {
		}
		try {
			splash2img = ImageIO.read(getClass().getResourceAsStream("/src/splash2edit.png"));
		} catch (IOException e) {
		}
		try {
			splash3img = ImageIO.read(getClass().getResourceAsStream("/src/splash3.png"));
		} catch (IOException e) {
		}
		try {
			backgroundimg = ImageIO.read(getClass().getResourceAsStream("/src/BackgroundEdit.png"));
		} catch (IOException e) {
		}
		spriteimg = new ImageIcon(getClass().getClassLoader().getResource("src/spriterun.gif"));
		try {
			ufoimg = ImageIO.read(getClass().getResourceAsStream("/src/ufoedit.png"));
		} catch (IOException e) {
		}
		try {
			beamimg = ImageIO.read(getClass().getResourceAsStream("/src/beamedit.png"));
		} catch (IOException e) {
		}
		try {
			jumpimg = ImageIO.read(getClass().getResourceAsStream("/src/jumpsprite.png"));
		} catch (IOException e) {
		}
		try {
			cowimg = ImageIO.read(getClass().getResourceAsStream("/src/cowedit.png"));
		} catch (IOException e) {
		}
		try {
			tractorimg = ImageIO.read(getClass().getResourceAsStream("/src/tractoredit.png"));
		} catch (IOException e) {
		}
		try {
			cowdeathimg = ImageIO.read(getClass().getResourceAsStream("/src/cowdeath.png"));
		} catch (IOException e) {
		}
		try {
			beamimg2 = ImageIO.read(getClass().getResourceAsStream("/src/beamedit2.png"));
		} catch (IOException e) {
		}
		try {
			damageimg = ImageIO.read(getClass().getResourceAsStream("/src/damagesprite.png"));
		} catch (IOException e) {
		}
		alienimg = new ImageIcon(getClass().getClassLoader().getResource("src/dancingalien.gif"));
		try {
			titleimg = ImageIO.read(getClass().getResourceAsStream("/src/title.png"));
		} catch (IOException e) {
		}
		ufogif = new ImageIcon(getClass().getClassLoader().getResource("src/ufogif.gif"));
	}

	/**
	 * Creates and sets text/icons for some JLabels. These will be used with a
	 * single instance within the game
	 */
	private void iconsetup() {
		splash1 = new JLabel();
		splash1.setIcon(new ImageIcon(splash1img));
		splash2 = new JLabel();
		splash2.setIcon(new ImageIcon(splash2img));
		splash3 = new JLabel();
		splash3.setIcon(new ImageIcon(splash3img));
		background1 = new JLabel();
		background1.setIcon(new ImageIcon(backgroundimg));
		background2 = new JLabel();
		background2.setIcon(new ImageIcon(backgroundimg));

		sprite = new JLabel();
		sprite.setIcon(spriteimg);
		scorelabel = new JLabel();
		scorelabel.setFont(new Font("Courier New", Font.BOLD, 16));
		scorelabel.setForeground(Color.WHITE);
		pausedlabel = new JLabel();
		pausedlabel.setFont(new Font("Courier New", Font.BOLD, 16));
		pausedlabel.setForeground(Color.WHITE);

		ufo = new JLabel();
		ufo.setIcon(new ImageIcon(ufoimg));
		beam = new JLabel();
		beam.setIcon(new ImageIcon(beamimg));
	}

	/**
	 * This method will run whenever the panel is switched to via bottom buttons
	 * - it will force resize the window and prevent resizing, then work out
	 * what actions need to be taken based upon the state of the game.
	 * If the game is running, it will request focus, and if the splash screen is due
	 * it will display the splash screen.
	 */
	public void switchTo() {
		gui.setSize(900, 720);
		gui.setResizeable(false);
		if (!splash) {

			splash = true;
			startButton = new JButton("Start game");

			instructions = new JLabel(
					"<html><div style='text-align: center;'>Kevin's farm appears to be under invasion.<br>Jump over the cows.<br>Avoid the tractors.<br>Run away from the aliens!</html>");
			instructions.setFont(new Font("Courier New", Font.BOLD, 16));
			instructions.setForeground(Color.WHITE);
			gameboard.add(instructions, new Integer(4));

			highScoreLabel = new JLabel(
					"<html><div style='text-align: center;'>Current high score: " + highScore + "</html>");
			highScoreLabel.setFont(new Font("Courier New", Font.BOLD, 16));
			highScoreLabel.setForeground(Color.WHITE);
			gameboard.add(highScoreLabel, new Integer(4));

			controls = new JLabel(
					"<html><div style='text-align: center;'>Controls:<br>Move left/right: Left/Right Arrow Keys<br>Jump: Spacebar/Up Arrow<br> Pause: P<br>Reset: Backspace</html>");
			controls.setFont(new Font("Courier New", Font.BOLD, 16));
			controls.setForeground(Color.WHITE);
			gameboard.add(controls, new Integer(4));

			titleLabel = new JLabel();
			titleLabel.setIcon(new ImageIcon(titleimg));
			gameboard.add(titleLabel, new Integer(4));

			gameboard.add(splash3, new Integer(0));
			splash3.setBounds(-50, 0, 990, 792);
			gameboard.add(splash2, new Integer(1));
			splash2.setBounds(-40, -100, 990, 792);
			gameboard.add(splash1, new Integer(2));
			splash1.setBounds(-40, -100, 990, 792);

			runStartLoop();
		}
		if (gameRunning) {
			// paused = false;
			gameboard.requestFocus();
		}
	}

	/**
	 * Creates a new thread to run the startLoop code
	 */
	private void runStartLoop() {
		Thread startloopthread = new Thread() {
			public void run() {
				startloop();
			}
		};
		startloopthread.start();
	}

	/**
	 * Creates a loop used to modulate and carry out the splash screen animation
	 * while it is due to be displayed
	 */
	private void startloop() {
		repaint();
		long lastLoopTime = System.currentTimeMillis();
		long counter = 0;
		while (splash) {
			repaint();
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			counter = counter + delta;
			if (counter < 3000) {
				splash1.setBounds(-50 + (int) counter / 70, -100 - (int) counter / 60, 990, 792);
			} else if (counter < 6000) {
				gameboard.remove(splash1);
				// splash1.setVisible(false);
				splash2.setBounds(-40 - (int) (counter - 3000) / 60, -100 + (int) counter / 60, 990, 792);
			} else if (counter < 8000) {
				gameboard.remove(splash2);
				// splash2.setVisible(false);
				splash3.setBounds(-50 + (int) (counter - 6000) / 60, 0 - (int) counter / 60, 990, 792);
			} else {
				instructions.setBounds(75, 330, 250, 200);
				controls.setBounds(525, 330, 300, 200);
				highScoreLabel.setBounds(700, 25, 200, 50);
				titleLabel.setBounds(240, 20, 420, 50);
				gameboard.add(startButton, new Integer(3));
				startButton.setBounds(30, 21, 100, 50);
				startButtonController = new StartButtonController(this);
				startButton.addActionListener(startButtonController);
				break;
			}
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Called when the panel is switched away from, this method checks to see if
	 * the gameboard needs to be reset (in the case where the game is not running), 
	 * or if the game needs to be paused (if it is running).
	 */
	public void checkClear() {
		if (gameRunning) {
			paused = true;
		} else {
			clear();
			splash = false;
			gameRunning = false;
			clear();
		}

	}

	/**
	 * Clears gameboard of all elements
	 */
	public void clear() {
		gameboard.removeAll();
		repaint();
	}

	/**
	 * Sets fields to default values required before the start of a game,
	 * then calls for the begin of the gameLoop.
	 */
	public void setupGame() {

		gameboard.add(background1, new Integer(0));
		gameboard.add(background2, new Integer(0));
		gameboard.add(sprite, new Integer(3));
		gameboard.add(scorelabel, new Integer(1));
		gameboard.add(pausedlabel, new Integer(1));
		gameboard.add(ufo, new Integer(6));
		gameboard.add(beam, new Integer(5));
		lives = 3;
		sprite.setIcon(spriteimg);
		playerpos = 2;
		gameover = false;
		anim1 = false;
		anim2 = false;
		jump = false;
		collided = false;
		paused = false;
		objectCounter = 0;
		draw();
		gameRunning = true;
		runGameLoop();
	}

	/**
	 * Creates a new thread to run the game loop
	 */
	private void runGameLoop() {
		Thread gameloopthread = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		gameloopthread.start();
	}

	/**
	 * The main method to this game. This method performs the loop which checks
	 * repeatedly what logic methods need to be carried out based upon the
	 * gamestate - including moving sprites, adding sprites, setting animations,
	 * pausing the game, determining if the game should be over.
	 */
	private void gameLoop() {

		gameboard.requestFocus();
		cows = new ArrayList();
		tractors = new ArrayList();
		draw();
		counter = 0;
		long lastLoopTime = System.currentTimeMillis();
		while (gameRunning) {

			if (!paused) {
				delta = System.currentTimeMillis() - lastLoopTime;

			} else {
				delta = 0;

			}
			lastLoopTime = System.currentTimeMillis();

			counter = counter + delta;
			objectCounter = objectCounter + delta;
			moveBackground(delta, (long) (200 + Math.sqrt(counter) * 2));

			if (!gameover) {
				draw();
				setScoreText(counter / 1000);

				X = sprite.getX();
				Y = sprite.getY();

				if (objectCounter > 1000) {
					distribute();
					objectCounter = 0;
				}

				if (!jump) {
					spriteCalc();
					jumpTimer = 0;
					tempY = sprite.getY();

				} else {
					if (collided) {
						sprite.setIcon(new ImageIcon(damageimg));
						spriteCalc();
						jumpTimer = 700;
					} else {
						sprite.setIcon(new ImageIcon(jumpimg));
						jump(delta);
					}

				}
				collisionDetection();
				moveObjects(delta, (long) (200 + Math.sqrt(counter) * 1.7));

				if (collided) {
					collisioncounter += delta;

					if (collisioncounter > 1600) {
						collided = false;
						sprite.setIcon(spriteimg);
						lives--;
					}
				}

			} else {
				deathanim(delta);
				for (int z = 0; z < cows.size(); z++) {
					if (!cows.get(z).alive())
						killCow(cows.get(z), delta);
				}
			}

			flickercounter += delta;
			if (flickercounter < 50) {
				beam.setIcon(new ImageIcon(beamimg));
			} else if (flickercounter < 100) {
				beam.setIcon(new ImageIcon(beamimg2));
			} else {
				flickercounter = 0;
			}

			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}

		}

		String message;
		if (counter / 1000 > highScore) {
			highScore = (int) counter / 1000 - 3;
			message = ("You have been captured by the aliens!\nYour final score is: " + ((int) counter / 1000 - 3)
					+ "\nCongratulations! New high score!!");
			save();
		} else {
			message = ("You have been captured by the aliens!\nYour final score is: " + ((int) counter / 1000 - 3));
		}

		JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.PLAIN_MESSAGE, ufogif);

		String[] options = new String[2];
		options[1] = new String("No");
		options[0] = new String("Yes");
		int reply = JOptionPane.showOptionDialog(null,
				"Thank you for playing!\nCreated by: Sean Seeds\nGraphics created by: Sean Seeds\nCast:\nKevin----------The Farmer\nKim-----------The Cow\nVitas----------The Alien\nMartin---------Billboard Star\nConcept: Sean Seeds\nScriptwriting: Sean Seeds\nCreated with: Blender3D + Adobe Photoshop + Java Swing\n2017 CS PPA Group Project\nWould you like to play again?",
				"Game Over", 0, JOptionPane.INFORMATION_MESSAGE, alienimg, options, null);
		if (reply == JOptionPane.YES_OPTION) {
			clear();
			setupGame();
		} else {
			clear();
			splash = false;
			switchTo();
		}
	}

	/**
	 * Saves the stored high score to a file
	 */
	private void save() {
		try {
			FileOutputStream fout = new FileOutputStream("HighScore.ufo");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(highScore);
			fout.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Attempts to load a high score from a file
	 */
	private void load() {
		try {
			FileInputStream fin = new FileInputStream("HighScore.ufo");
			ObjectInputStream ois = new ObjectInputStream(fin);
			highScore = (int) ois.readObject();
			fin.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Detects if the player has collided with any object and carries out
	 * the relevant code if this is found to be true
	 */
	private void collisionDetection() {

		for (int z = 0; z < cows.size(); z++) { // cowcheck
			if (!jump && !collided) {
				if (cows.get(z).getPos() == playerpos) {
					if (playerpos == 1) {
						if (cows.get(z).getX() > sprite.getX() - 40 && cows.get(z).getX() < sprite.getX() + 30) {
							collided = true;
							jump = false;
							collisioncounter = 0;
							sprite.setIcon(new ImageIcon(damageimg));

						}
					} else if (playerpos == 2) {
						if (cows.get(z).getX() > sprite.getX() - 30 && cows.get(z).getX() < sprite.getX() + 40) {
							collided = true;
							jump = false;
							collisioncounter = 0;
							sprite.setIcon(new ImageIcon(damageimg));

						}
					} else if (playerpos == 3) {
						if (cows.get(z).getX() > sprite.getX() - 10 && cows.get(z).getX() < sprite.getX() + 60) {
							collided = true;
							jump = false;
							collisioncounter = 0;
							sprite.setIcon(new ImageIcon(damageimg));

						}
					}
				}
			}
		}

		for (int z = 0; z < tractors.size(); z++) { // tractorcheck
			if (!collided) {
				if (tractors.get(z).getPos() == playerpos) {
					if (playerpos == 1) {
						if (tractors.get(z).getX() > sprite.getX() - 40
								&& tractors.get(z).getX() < sprite.getX() + 30) {
							collided = true;
							collisioncounter = 0;
							sprite.setIcon(new ImageIcon(damageimg));

						}
					} else if (playerpos == 2 && tractors.get(z).getPos() == 2) {
						if (tractors.get(z).getX() > sprite.getX() - 20
								&& tractors.get(z).getX() < sprite.getX() + 30) {
							collided = true;
							collisioncounter = 0;
							sprite.setIcon(new ImageIcon(damageimg));

						}
					} else if (playerpos == 3) {
						if (tractors.get(z).getX() > sprite.getX() - 10
								&& tractors.get(z).getX() < sprite.getX() + 60) {
							collided = true;
							collisioncounter = 0;
							sprite.setIcon(new ImageIcon(damageimg));

						}
					}
				}
			}
		}

	}

	/**
	 * Uses random number generation to decide what permutation of obstacles
	 * needs to be added to the gameboard
	 */
	private void distribute() {
		if (rand.nextInt(3) < 2) {
			int z = rand.nextInt(4);

			for (int i = 0; i < z; i++) {
				cows.add(new cow());
				cows.get(cows.size() - 1).setIcon(new ImageIcon(cowimg));
				gameboard.add(cows.get(cows.size() - 1), new Integer(4));

				int pos = rand.nextInt(3) + 1;

				if (pos == 3) {
					cows.get(cows.size() - 1).setPos(3);
					cows.get(cows.size() - 1).setx(860);
					cows.get(cows.size() - 1).sety(560);
				} else if (pos == 2) {
					cows.get(cows.size() - 1).setPos(2);
					cows.get(cows.size() - 1).setx(770);
					cows.get(cows.size() - 1).sety(620);
				} else if (pos == 1) {
					cows.get(cows.size() - 1).setPos(1);
					cows.get(cows.size() - 1).setx(660);
					cows.get(cows.size() - 1).sety(680);
				}
				cows.get(cows.size() - 1).setDestination(cows.get(cows.size() - 1).getx() - 980,
						cows.get(cows.size() - 1).gety() - 550);
			}

		} else {

			tractors.add(new cow());

			tractors.get(tractors.size() - 1).setIcon(new ImageIcon(tractorimg));
			gameboard.add(tractors.get(tractors.size() - 1), new Integer(4));

			int pos = rand.nextInt(3) + 1;

			if (pos == 1) {
				tractors.get(tractors.size() - 1).setPos(1);
				tractors.get(tractors.size() - 1).setx(690);
				tractors.get(tractors.size() - 1).sety(680);
			} else if (pos == 2) {
				tractors.get(tractors.size() - 1).setPos(2);
				tractors.get(tractors.size() - 1).setx(800);
				tractors.get(tractors.size() - 1).sety(620);
			} else if (pos == 3) {
				tractors.get(tractors.size() - 1).setPos(3);
				tractors.get(tractors.size() - 1).setx(890);
				tractors.get(tractors.size() - 1).sety(560);
			}
			tractors.get(tractors.size() - 1).setDestination(tractors.get(tractors.size() - 1).getx() - 980,
					tractors.get(tractors.size() - 1).gety() - 550);
		}
	}

	/**
	 * Carries out logic to determine the required position of any obstacles.
	 * It uses the delta value of the gameLoop as well as a calculated speed to
	 * mathematically determine how to update the coordinates of each object.
	 * 
	 * @param delta
	 * @param speed
	 */
	private void moveObjects(long delta, long speed) {
		for (int z = 0; z < cows.size(); z++) {

			if (cows.get(z).alive()) {
				float time = (cows.get(z).getDestinationX() - cows.get(z).getx()) / (speed);
				cows.get(z).setx(cows.get(z).getx()
						- (delta * (cows.get(z).getDestinationX() - cows.get(z).getx()) / time) / 1000);
				cows.get(z).sety(cows.get(z).gety()
						- (delta * (cows.get(z).getDestinationY() - cows.get(z).gety()) / time) / 1000);
				cows.get(z).setBounds((int) cows.get(z).getx(), (int) cows.get(z).gety(), 174, 137);
				// cows.get(z).setBounds(690, 620, 174, 137);
			} else {
				if (cows.get(z).anim1()) {

					cows.get(z).setx(cows.get(z).getx() + (delta * (-10 - cows.get(z).getx())) / 300);
					cows.get(z).sety(cows.get(z).gety() + (delta * (150 - cows.get(z).gety())) / 300);
				} else if (cows.get(z).anim2()) {

					cows.get(z).setIcon(new ImageIcon(cowdeathimg));
					cows.get(z).setx(cows.get(z).getx() + (delta * (-10 - cows.get(z).getx())) / 300);
					cows.get(z).sety(cows.get(z).gety() + (delta * (68 - cows.get(z).gety())) / 300);
				}

				if ((cows.get(z).getY() == 149 || cows.get(z).getY() == 150)
						&& (cows.get(z).getX() == -9 || cows.get(z).getX() == -10)) {
					cows.get(z).setanim1(false);
					cows.get(z).setanim2(true);
				}
				if ((cows.get(z).getY() == 68 || cows.get(z).getY() == 69)
						&& (cows.get(z).getX() == -10 || cows.get(z).getX() == -9)) {
					cows.get(z).setanim2(false);
					gameboard.remove(cows.get(z));
				}
				cows.get(z).setBounds((int) cows.get(z).getx(), (int) cows.get(z).gety(), 174, 137);
			}

			if (cows.get(z).getPos() > playerpos) {
				gameboard.setLayer(cows.get(z), new Integer(2));
			} else if (cows.get(z).getPos() == playerpos) {
				if (cows.get(z).getX() > sprite.getX()) {
					gameboard.setLayer(cows.get(z), new Integer(4));
				} else {
					gameboard.setLayer(cows.get(z), new Integer(2));
				}
			} else {
				gameboard.setLayer(cows.get(z), new Integer(4));
			}

		}

		for (int s = 0; s < tractors.size(); s++) {

			float time = (tractors.get(s).getDestinationX() - tractors.get(s).getx()) / (speed);
			tractors.get(s).setx(tractors.get(s).getx()
					- (delta * (tractors.get(s).getDestinationX() - tractors.get(s).getx()) / time) / 1000);
			tractors.get(s).sety(tractors.get(s).gety()
					- (delta * (tractors.get(s).getDestinationY() - tractors.get(s).gety()) / time) / 1000);
			tractors.get(s).setBounds((int) tractors.get(s).getx(), (int) tractors.get(s).gety(), 174, 137);
			// cows.get(z).setBounds(690, 620, 174, 137);

			if (tractors.get(s).getPos() > playerpos) {
				gameboard.setLayer(tractors.get(s), new Integer(2));
			} else if (tractors.get(s).getPos() == playerpos) {
				if (tractors.get(s).getX() > sprite.getX()) {
					gameboard.setLayer(tractors.get(s), new Integer(4));
				} else {
					gameboard.setLayer(tractors.get(s), new Integer(2));
				}
			} else {
				gameboard.setLayer(tractors.get(s), new Integer(4));
			}
		}

		for (int a = cows.size() - 1; a > -1; a--) {

			if (cows.get(a).getPos() == 1) {
				if (cows.get(a).getx() < -50) {
					cows.get(a).setAlive(false);
				}
			} else if (cows.get(a).getPos() == 2) {
				if (cows.get(a).getx() < 50) {
					cows.get(a).setAlive(false);
				}
			} else {
				if (cows.get(a).getx() < 150) {
					cows.get(a).setAlive(false);
				}
			}
		}

		for (int a = tractors.size() - 1; a > -1; a--) {
			if (tractors.get(a).getx() < -150) {
				gameboard.remove(tractors.get(a));
			}
		}
	}

	/**
	 * Runs death animation on a cow object, used when the main gameloop is no
	 * longer calling moveObjects, but non-alive cows still require death animation to play
	 * 
	 * @param Cow
	 * @param delta
	 */
	private void killCow(cow Cow, long delta) {
		if (Cow.anim1()) {

			Cow.setx(Cow.getx() + (delta * (-10 - Cow.getx())) / 300);
			Cow.sety(Cow.gety() + (delta * (150 - Cow.gety())) / 300);
		} else if (Cow.anim2()) {

			Cow.setIcon(new ImageIcon(cowdeathimg));
			Cow.setx(Cow.getx() + (delta * (-10 - Cow.getx())) / 300);
			Cow.sety(Cow.gety() + (delta * (68 - Cow.gety())) / 300);
		}

		if ((Cow.getY() == 149 || Cow.getY() == 150) && (Cow.getX() == -9 || Cow.getX() == -10)) {
			Cow.setanim1(false);
			Cow.setanim2(true);
		}
		if ((Cow.getY() == 68 || Cow.getY() == 69) && (Cow.getX() == -10 || Cow.getX() == -9)) {
			Cow.setanim2(false);
			gameboard.remove(Cow);
		}
		Cow.setBounds((int) Cow.getx(), (int) Cow.gety(), 174, 137);
	}

	/**
	 * Carries out logic to work out required position of background images,
	 * based on delta from gameLoop and the calculated speed. Also handles
	 * looping of the background image to ensure seamless scrolling
	 * 
	 * @param delta
	 * @param speed
	 */
	private void moveBackground(long delta, long speed) {
		
		// Start screen coords: 0,-620
		// Begin from bottom coords: 900, -100

		if (back1) {
			float time = (-5070 - backX) / (speed);
			backX -= (delta * (-5070 - backX) / time) / 1000;
			backY -= (delta * (-3550 - backY) / time) / 1000;
		}
		if (back2) {
			float time = (-5070 - backX2) / (speed);
			backX2 -= (delta * (-5070 - backX2) / time) / 1000;
			backY2 -= (delta * (-3550 - backY2) / time) / 1000;
		}

		// back2 needs to begin to move
		if (backX < -4075) {
			back2 = true;
		}
		if (backX2 < -4075) {
			back1 = true;
		}

		// return to bottom when offscreen
		if (backX2 < -5065) {
			back2 = false;
			backX2 = 900;
			backY2 = -100;
		}
		if (backX < -5065) {
			back1 = false;
			backX = 900;
			backY = -100;
		}

	}

	/**
	 * Updates text for JLabel pertaining to the player's score
	 */
	private void setScoreText(long score) {
		scorelabel.setText("Score: " + score);
	}

	/**
	 * Calculates the required position of player sprite based on gamestate and draws accordingly.
	 */
	private void spriteCalc() {

		if (lives == 3) {
			if (playerpos == 1) {
				sprite.setBounds(200, 370, 195, 163);
			} else if (playerpos == 2) {
				sprite.setBounds(270, 320, 195, 163);
			} else {
				sprite.setBounds(340, 270, 195, 163);
			}
		} else if (lives == 2) {
			if (playerpos == 1) {
				sprite.setBounds(130, 320, 195, 163);
			} else if (playerpos == 2) {
				sprite.setBounds(200, 270, 195, 163);
			} else {
				sprite.setBounds(270, 230, 195, 163);
			}
		} else if (lives == 1) {
			if (playerpos == 1) {
				sprite.setBounds(60, 270, 195, 163);
			} else if (playerpos == 2) {
				sprite.setBounds(130, 220, 195, 163);
			} else {
				sprite.setBounds(200, 170, 195, 163);
			}
		} else {
			sprite.setIcon(new ImageIcon(damageimg));
			if (playerpos == 1) {
				sprite.setBounds(-10, 220, 195, 163);
			} else if (playerpos == 2) {
				sprite.setBounds(60, 170, 195, 163);
			} else {
				sprite.setBounds(130, 120, 195, 163);
			}
			gameover = true;
			anim1 = true;
		}

	}

	/**
	 * Draws positions of non-volatile objects (background, UFO, beam)
	 */
	private void draw() {

		scorelabel.setBounds(400, 0, 100, 50);
		pausedlabel.setBounds(550, 0, 350, 50);
		ufo.setBounds(0, 65, 229, 226);
		beam.setBounds(-5, 125, 223, 187);
		background1.setBounds((int) backX, (int) backY, 5625, 4500);
		background2.setBounds((int) backX2, (int) backY2, 5625, 4500);
	}

	/**
	 * Logic pertaining to the player's ability to jump, will play a jump
	 * aniamtion for a set period instead of the usual static running animation
	 * 
	 * @param delta
	 */
	private void jump(long delta) {

		jumpTimer += delta;
		if (jumpTimer < 300) {
			// gameboard.setLayer(sprite, new Integer(7));
			Y -= (delta * (tempY + 250 - Y)) / 900;
			X += (delta * 175) / 1000;
		} else if (jumpTimer < 600) {

			Y += (delta * (tempY + 300 - Y)) / 900;
			X -= (delta * 175) / 1000;
		} else {
			sprite.setIcon(spriteimg);
			gameboard.setLayer(sprite, new Integer(2));
			jump = false;
		}

		sprite.setBounds((int) X, (int) Y, 195, 163);
	}

	/**
	 * Plays a death animation for the player sprite. Used when the player has
	 * run out of lives
	 * 
	 * @param delta
	 */
	private void deathanim(long delta) {

		if (anim1) {

			X += (delta * (-10 - X)) / 300;
			Y += (delta * (150 - Y)) / 300;
		} else if (anim2) {
			sprite.setIcon(new ImageIcon(jumpimg));
			X += (delta * (-10 - X)) / 300;
			Y += (delta * (57 - Y)) / 300;
		}

		if ((sprite.getY() == 149 || sprite.getY() == 150) && (sprite.getX() == -9 || sprite.getX() == -10)) {
			anim1 = false;
			anim2 = true;
		}
		if ((sprite.getY() == 57 || sprite.getY() == 58) && (sprite.getX() == -10 || sprite.getX() == -9)) {
			anim2 = false;
			gameRunning = false;
			gameboard.remove(sprite);
		}

		sprite.setBounds((int) X, (int) Y, 195, 163);

		// sprite.setBounds(-10, 150, 195, 163);
		// sprite.setBounds(-10, 57, 195, 163);
	}

	/**
	 * Toggles state of the paused flag, updates indicator JLabel accordingly
	 */
	private void togglePause() {
		if (paused) {
			paused = false;
			pausedlabel.setText("");
		} else {
			paused = true;
			pausedlabel.setText("Game paused: Press P to resume.");
		}
	}
}
