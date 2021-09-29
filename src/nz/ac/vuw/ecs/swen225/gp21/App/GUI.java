package nz.ac.vuw.ecs.swen225.gp21.App;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import nz.ac.vuw.ecs.swen225.gp21.Persistency.XMLSaveLoad;
import nz.ac.vuw.ecs.swen225.gp21.domain.Game;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecordAndPlay;
import nz.ac.vuw.ecs.swen225.gp21.renderer.Board;
import nz.ac.vuw.ecs.swen225.gp21.renderer.RenderTile;

public class GUI {



	private MainFrame mainFrame;
	private JPanel collectedKeysPanel;
	private JLabel yellowKey;
	private JLabel greenKey;
	private JLabel blueKey;
	InfoPanel levelPanel;
	InfoPanel timePanel;
	InfoPanel chipsPanel;


	private int level;
	private int timeLeft;
	private int treasuresLeft;
	private boolean ctrlPressed = false;
	private RecordAndPlay gameHistory;

	private TimerTask task;
    private Timer timer;



	private Game currentGame;
	private Board renderBoard;
	
	
	ImageIcon keyY = new ImageIcon(RenderTile.class.getResource("keyyellow.png"));
	ImageIcon keyG = new ImageIcon(RenderTile.class.getResource("keygreen.png"));
	ImageIcon keyB = new ImageIcon(RenderTile.class.getResource("keyblue.png"));


	public GUI(String filename) throws FontFormatException, IOException, InterruptedException {
		load(filename);
		updateInfo();
		gameHistory = new RecordAndPlay();
		mainFrame = new MainFrame("Chip's Challenge-Level "+level);
		mainFrame.setLayout(null);

		//create the menu bar
		JMenuItem saveMenuItem = mainFrame.getSaveItem();
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int saving = save();
				if(saving>0) {
					JOptionPane.showMessageDialog(mainFrame, "Game Saved!");
				}else {
					JOptionPane.showMessageDialog(mainFrame,"Saving failed!");

				}
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
				int loading = load("level1.xml");
				if(loading<0) {
					JOptionPane.showMessageDialog(mainFrame,"Saving failed!");
					return;
				}
				mainFrame.setVisible(false);
				mainFrame.dispose();
				try {
					new GUI("level1.xml");
				} catch (FontFormatException | IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mainFrame.setLevel1Item(level1MenuItem);

		JMenuItem level2MenuItem = mainFrame.getLevel2Item();
		level2MenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int loading = load("level2.xml");
				if(loading<0) {
					JOptionPane.showMessageDialog(mainFrame,"Saving failed!");
					return;
				}
				mainFrame.setVisible(false);
				mainFrame.dispose();
				try {
					new GUI("level2.xml");
				} catch (FontFormatException | IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mainFrame.setLevel2Item(level2MenuItem);
		
		mainFrame.createMenuBar();



		//game board panel settings
		renderBoard = new Board(this.currentGame);
		JComponent gameBoard = renderBoard.getGameBoard();
		int gameBoardWidth = mainFrame.getWidth()*2/3;
		int boardBorder = border(gameBoardWidth, mainFrame.getHeight());
		gameBoard.setBounds(boardBorder, boardBorder, gameBoardWidth-2*boardBorder, mainFrame.getHeight()-3*boardBorder);
		gameBoard.setBackground(new Color(190,190,190));
		gameBoard.setLayout(new GridLayout(9, 9));

		
		//game collectedKeysPanel
		collectedKeysPanel = new JPanel();
		collectedKeysPanel.setLayout(new GridLayout(3, 1));
		collectedKeysPanel.setBounds(gameBoardWidth-boardBorder, boardBorder, boardBorder*2, mainFrame.getHeight()/4);
		collectedKeysPanel.setBorder(new LineBorder(new Color(180,180,180), 3));
		yellowKey = new JLabel();
		yellowKey.setIcon(keyY);
		greenKey = new JLabel();
		greenKey.setIcon(keyG);
		blueKey = new JLabel();
		blueKey.setIcon(keyB);

		//The game info panel settings
		JPanel levelInfoPanel = new JPanel();
		levelInfoPanel.setLayout(new GridLayout(4, 1));
		levelInfoPanel.setBounds(gameBoardWidth+boardBorder, boardBorder, gameBoardWidth/2-2*boardBorder, mainFrame.getHeight()-3*boardBorder);
		levelInfoPanel.setBackground(new Color(190,190,190));


		levelPanel = new InfoPanel("LEVEL", level);
		timePanel = new InfoPanel("TIME", timeLeft);
		chipsPanel = new InfoPanel("CHIPS LEFT", treasuresLeft);

		JPanel buttonsPanel = new JPanel(new GridLayout(2, 3));
		JButton[] buttons = new JButton[6];
		buttons[0] = createButton("⏸", 0);
		buttons[1] = createButton("⬆", 1);
		buttons[2] = createButton("↩", 2);
		buttons[3] = createButton("⬅", 3);
		buttons[4] = createButton("⬇", 4);
		buttons[5] = createButton("➡", 5);
		for(int i=0 ; i<6 ; i++) {
			buttonsPanel.add(buttons[i]);
		}
		buttonsPanel.setBorder(new LineBorder(new Color(180, 180,180), 20));


		levelInfoPanel.add(levelPanel);
		levelInfoPanel.add(timePanel);
		levelInfoPanel.add(chipsPanel);
		levelInfoPanel.add(buttonsPanel);

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
					try {
						CtrlPressedActions(e);
					} catch (FontFormatException | IOException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});



		startTimer(true);
		
		
		

		mainFrame.add(gameBoard);
		mainFrame.add(levelInfoPanel);
		mainFrame.add(collectedKeysPanel);
		mainFrame.setVisible(true);
	}



	private int border(int width, int height) {
		int num = width<height? height : width;
		return (int)((0.05)*num);
	}
	
	private JButton createButton(String label, int action) {
		JButton button = new JButton(label);
		button.setBackground(Color.black);
		button.setForeground(Color.white);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch(action) {
					case 0:
						currentGame.pauseGame();
						startTimer(false);
						JOptionPane.showMessageDialog(new JFrame(), "Game Paused");
						currentGame.resumeGame();
						startTimer(true);
						break;
					case 1:
						currentGame.moveChap("w");
						break;
					case 2:
						int restarting = JOptionPane.showOptionDialog(new JFrame(), "Are you sure you want to restart this level?", "Restart", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, e);
						if(restarting==JOptionPane.YES_OPTION) {
							mainFrame.setVisible(false);
							startTimer(false);
							currentGame.pauseGame();
							mainFrame.dispose();
							try {
								new GUI("level"+level+".xml");
							} catch (FontFormatException | IOException | InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						break;
					case 3:
						currentGame.moveChap("a");
						break;
					case 4:
						currentGame.moveChap("s");
						break;
					case 5:
						currentGame.moveChap("d");
						break;
				}
				if(!currentGame.isPaused()) {
					updateInfo();
					renderBoard.redraw(renderBoard.getGameBoard().getGraphics());
				}
			}
		});
		return button;
	}

	private void CtrlPressedActions(KeyEvent e) throws FontFormatException, IOException, InterruptedException{
		if(e.getKeyCode()==88) {
			int result = JOptionPane.showOptionDialog(mainFrame, "Game is not saved. Do you want to save it before exiting?", null, JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Yes", "No", "Cancel"}, null);
			if(result!=JOptionPane.CANCEL_OPTION) {
				if(result==JOptionPane.YES_OPTION) {
					int saving = save();
					if(saving<0) {
						JOptionPane.showMessageDialog(mainFrame,"Saving failed!");
						return;
					}
				}
				mainFrame.setVisible(false);
				mainFrame.dispose();
			}
		}else if(e.getKeyCode()==83) {
			int saving = save();
			if(saving<0) {
				JOptionPane.showMessageDialog(mainFrame,"Saving failed!");
				return;
			}
			mainFrame.setVisible(false);
			mainFrame.dispose();
			JOptionPane.showMessageDialog(new JFrame(),"Saved and Exiting the game.");
		}else if(e.getKeyCode()==82) {
			updateInfo();
			JOptionPane.showMessageDialog(new JFrame(),"Resuming an already saved game.");
		}else if(e.getKeyCode()==49) {
			int loading = load("level1.xml");
			if(loading<0) {
				JOptionPane.showMessageDialog(mainFrame,"Saving failed!");
				return;
			}
			mainFrame.setVisible(false);
			mainFrame.dispose();
			new GUI("level1.xml");
		}else if(e.getKeyCode()==50) {
			int loading = load("level2.xml");
			if(loading<0) {
				JOptionPane.showMessageDialog(mainFrame,"Saving failed!");
				return;
			}
			mainFrame.setVisible(false);
			mainFrame.dispose();
			new GUI("level2.xml");
		}
	}



	private void CtrlNotPressedActions(KeyEvent e) {
		if(e.getKeyCode()==32) {
			startTimer(false);
			currentGame.pauseGame();
			int optionChosen = JOptionPane.showOptionDialog(mainFrame, "Game is paused", null, JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
			if(optionChosen==JOptionPane.CLOSED_OPTION) {
				startTimer(true);
			}
		}else if(e.getKeyCode()==27) {
			currentGame.resumeGame();
			//JOptionPane.showMessageDialog(new JFrame(),"Resumes the game.");
		}else if(e.getKeyCode()==37) {
			currentGame.moveChap("a");
			gameHistory.addMoves("a");
			//JOptionPane.showMessageDialog(new JFrame(),"Moving left");
		}else if(e.getKeyCode()==38) {
			currentGame.moveChap("w");
			gameHistory.addMoves("w");
			//JOptionPane.showMessageDialog(new JFrame(),"Moving up");
		}else if(e.getKeyCode()==39) {
			currentGame.moveChap("d");
			gameHistory.addMoves("d");
			//JOptionPane.showMessageDialog(new JFrame(),"Moving right");
		}else if(e.getKeyCode()==40) {
			currentGame.moveChap("s");
			gameHistory.addMoves("s");
			//JOptionPane.showMessageDialog(new JFrame(),"Moving down");
		}else {
			return;
		}
		updateInfo();
		renderBoard.redraw(renderBoard.getGameBoard().getGraphics());
	}

	private void updateInfo() {
		this.level = currentGame.getLevel();
		this.timeLeft = currentGame.timeLeft();
		this.treasuresLeft = currentGame.countTreasure();
		if(chipsPanel!=null) {
			chipsPanel.updateValue(this.treasuresLeft);
			currentGame.updateActors();
			for(String key : currentGame.getKeys()) {
				if(key==null) return;
				switch(key) {
					case "y":
						collectedKeysPanel.add(yellowKey);
						//collectedKeysPanel.add(yellowKey);
						break;
					case "g":
						collectedKeysPanel.add(greenKey);
						break;
					case "b":
						collectedKeysPanel.add(blueKey);
						break;
					//default :
						//throw new IllegalArgumentException();
				}
			}
		}
	}


	private void startTimer(boolean gameOn) {
		if(gameOn) {
			 task = new TimerTask() {
			        public void run() {
			        	if(currentGame.getGameOver() || timeLeft==-1) {
			        		int finishing=-10;
			        		startTimer(false);
			        		if(timeLeft==-1) {
			        			finishing = JOptionPane.showConfirmDialog(new JFrame(), "GAME OVER\nYou ran out of time!\nRestart this level?", "Game Over", JOptionPane.YES_NO_OPTION);
			        		}else if(level==1){
			        			finishing = JOptionPane.showConfirmDialog(new JFrame(), "Level completed!\n Go to the next level?", "Level Completed", JOptionPane.YES_NO_OPTION);
			        		}else if(level==2) {
			        			JOptionPane.showMessageDialog(new JFrame(), "CONGRADULATIONS!\nYou've completed the game", "Game Completed", JOptionPane.INFORMATION_MESSAGE);
			        		}
			        		mainFrame.setVisible(false);
			        		mainFrame.dispose();
			        		if(finishing == JOptionPane.YES_OPTION) {
		        				try {
		        					if(timeLeft==-1) {
		        						new GUI("level"+level+".xml");
		        					}else {
		        						new GUI("level"+(level+1)+".xml");
		        					}
								} catch (FontFormatException | IOException | InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        			}else {
		        				new StartingFrame("Chip's Challenge");
		        			}
			    	    	return;
			        	}
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
	
	
	private int save() {
			String saveFilename = JOptionPane.showInputDialog("Please enter a name for the file:");
			if(saveFilename==null || saveFilename.equals("")) {
				return -1;
			}
			try {
				XMLSaveLoad.save(currentGame, saveFilename);
				gameHistory.save(gameHistory);
				return 1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return -1;
			}
	}
	private int load(String filename) {

		try {
			currentGame =  XMLSaveLoad.load(filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			return -1;
		}
		return 1;
	}



	public static void main(String[] args) throws FontFormatException, IOException, InterruptedException {
		//Timer update
		//new GUI();
		new StartingFrame("Chip's Challenge");
	}
}
