package nz.ac.vuw.ecs.swen225.gp21.App;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;

import nz.ac.vuw.ecs.swen225.gp21.Persistency.XMLSaveLoad;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;

public class GUI {



	private MainFrame mainFrame;
	InfoPanel levelPanel;
	InfoPanel timePanel;
	InfoPanel chipsPanel;


	private int level;
	private int timeLeft=100;
	private boolean ctrlPressed = false;
	private boolean gameOn = false;

	private TimerTask task;
    private Timer timer;



	private Game currentGame;


	public GUI() throws FontFormatException, IOException, InterruptedException {
		//set name and icon of the frame
		mainFrame = new MainFrame("Chip's Challenge-Level "+level);
		mainFrame.setLayout(null);


		//create the menu bar
		JMenuItem saveMenuItem = mainFrame.getSaveItem();
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				XMLSaveLoad.save(currentGame);
				JOptionPane.showMessageDialog(mainFrame, "Game Saved!");
			}
		});
		mainFrame.setSaveItem(saveMenuItem);

		JMenuItem loadMenuItem = mainFrame.getLoadItem();
		loadMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//XMLSaveLoad.load();
				updateInfo();
				JOptionPane.showMessageDialog(mainFrame, "Loading already saved game!");
			}
		});
		mainFrame.setLoadItem(loadMenuItem);

		JMenuItem level1MenuItem = mainFrame.getLevel1Item();
		level1MenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentGame = XMLSaveLoad.load(1);
				updateInfo();
			}
		});
		mainFrame.setLevel1Item(level1MenuItem);

		JMenuItem level2MenuItem = mainFrame.getLevel2Item();
		level2MenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currentGame = XMLSaveLoad.load(2);
				updateInfo();
			}
		});
		mainFrame.setLevel2Item(level2MenuItem);

		mainFrame.createMenuBar();



		//game board panel settings
		JPanel gameBoard = new JPanel();
		int gameBoardWidth = mainFrame.getWidth()*2/3;
		int boardBorder = border(gameBoardWidth, mainFrame.getHeight());
		gameBoard.setBounds(boardBorder, boardBorder, gameBoardWidth-2*boardBorder, mainFrame.getHeight()-3*boardBorder);
		gameBoard.setBackground(new Color(190,190,190));
		gameBoard.setLayout(new GridLayout(9, 9));


		//The game info panel settings
		JPanel levelInfoPanel = new JPanel();
		levelInfoPanel.setLayout(new GridLayout(4, 1));
		levelInfoPanel.setBounds(gameBoardWidth+boardBorder, boardBorder, gameBoardWidth/2-2*boardBorder, mainFrame.getHeight()-3*boardBorder);
		levelInfoPanel.setBackground(new Color(190,190,190));


		levelPanel = new InfoPanel("LEVEL", level);
		timePanel = new InfoPanel("TIME", 100);
		chipsPanel = new InfoPanel("CHIPS LEFT", 0);

		JPanel collectedItemsPanel = new JPanel(new GridLayout(2, 4));
		JLabel[] collectedItemsTileLabels = new JLabel[8];
		ImageIcon collectedItemsTile = new ImageIcon("../chip-challenge/src/nz/ac/vuw/ecs/swen225/gp21/App/CollectedItemsTile.png");
		System.out.println(collectedItemsTile.getIconHeight());
		System.out.println(collectedItemsTile.getIconWidth());
		for(int i=0 ; i<2 ; i++) {
			collectedItemsTileLabels[i] = new JLabel(collectedItemsTile);
			collectedItemsPanel.add(collectedItemsTileLabels[i]);
		}


		levelInfoPanel.add(levelPanel);
		levelInfoPanel.add(timePanel);
		levelInfoPanel.add(chipsPanel);
		levelInfoPanel.add(collectedItemsPanel);

		//adding keyboard listeners
		mainFrame.setFocusable(true);
	    mainFrame.requestFocus();
	    mainFrame.requestFocusInWindow();
		//key listeners
		mainFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==17) {
					ctrlPressed = false;
				}else {
					CtrlNotPressedActions(e);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==17) {
					ctrlPressed = true;
				}else if(ctrlPressed) {
					CtrlPressedActions(e);
				}
			}
		});



		startTimer(true);

		mainFrame.add(gameBoard);
		mainFrame.add(levelInfoPanel);
		mainFrame.setVisible(true);
	}



	private int border(int width, int height) {
		int num = width<height? height : width;
		return (int)((0.05)*num);
	}

	private void CtrlPressedActions(KeyEvent e) {
		if(e.getKeyCode()==88) {
			int result = JOptionPane.showOptionDialog(mainFrame, "Game is not saved. Do you want to save it before exiting?", null, JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Yes", "No", "Cancel"}, null);
			if(result!=JOptionPane.CANCEL_OPTION) {
				if(result==JOptionPane.YES_OPTION) {
				XMLSaveLoad.save(currentGame);
				}
				mainFrame.setVisible(false);
				mainFrame.dispose();
			}
		}else if(e.getKeyCode()==83) {
			XMLSaveLoad.save(currentGame);
			mainFrame.setVisible(false);
			mainFrame.dispose();
			JOptionPane.showMessageDialog(new JFrame(),"Saved and Exiting the game.");
		}else if(e.getKeyCode()==82) {
			updateInfo();
			JOptionPane.showMessageDialog(new JFrame(),"Resuming an already saved game.");
		}else if(e.getKeyCode()==49) {
			currentGame = XMLSaveLoad.load(1);
			updateInfo();
			JOptionPane.showMessageDialog(new JFrame(),"Start a game from level 1.");
		}else if(e.getKeyCode()==50) {
			currentGame = XMLSaveLoad.load(2);
			updateInfo();
			JOptionPane.showMessageDialog(new JFrame(),"Start a game from level 2.");
		}
	}



	private void CtrlNotPressedActions(KeyEvent e) {
		if(e.getKeyCode()==32) {
			startTimer(false);
			int optionChosen = JOptionPane.showOptionDialog(mainFrame, "Game is paused", null, JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
			if(optionChosen==JOptionPane.CLOSED_OPTION) {
				startTimer(true);
			}
		}else if(e.getKeyCode()==27) {
			//JOptionPane.showMessageDialog(new JFrame(),"Resumes the game.");
		}else if(e.getKeyCode()==37) {
			currentGame.moveChap("a");
			JOptionPane.showMessageDialog(new JFrame(),"Moving left");
		}else if(e.getKeyCode()==38) {
			currentGame.moveChap("w");
			JOptionPane.showMessageDialog(new JFrame(),"Moving up");
		}else if(e.getKeyCode()==39) {
			currentGame.moveChap("d");
			JOptionPane.showMessageDialog(new JFrame(),"Moving right");
		}else if(e.getKeyCode()==40) {
			currentGame.moveChap("s");
			JOptionPane.showMessageDialog(new JFrame(),"Moving down");
		}
	}

	private void updateInfo() {
		this.level = currentGame.getLevel();
		this.timeLeft = currentGame.timeLeft();
	}


	private void startTimer(boolean gameOn) {
		if(gameOn) {
			 task = new TimerTask() {
			        public void run() {
			        	timePanel.updateValue(timeLeft--);
			        }
			    };
			timer = new Timer("Timer");
	    	timer.schedule(task, 0, 1000);
	    }else if(timer!=null){
	    	timer.cancel();
	    	task=null;
	    	timer = null;
	    }

	}




	public static void main(String[] args) throws FontFormatException, IOException, InterruptedException {
		//Timer update
		new GUI();
	}
}
