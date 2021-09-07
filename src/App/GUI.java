package App;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class GUI {

	private JFrame mainFrame;
	private int level = 1;
	private boolean ctrlPressed = false;
	
	
	public GUI() {
		//set name and icon of the frame
		mainFrame = new MainFrame("Chip's Challenge-Level "+level);
		mainFrame.setLayout(null);
		
		
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
		
		InfoPanel levelPanel = new InfoPanel("LEVEL", level);
		InfoPanel timePanel = new InfoPanel("TIME", 100);
		InfoPanel chipsPanel = new InfoPanel("CHIPS LEFT", 0);
		
		
		levelInfoPanel.add(levelPanel);
		levelInfoPanel.add(timePanel);
		levelInfoPanel.add(chipsPanel);
		
		
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
			JOptionPane.showMessageDialog(new JFrame(),"Exiting the game without saving.");
		}else if(e.getKeyCode()==83) {
			JOptionPane.showMessageDialog(new JFrame(),"Saved and Exiting the game.");
		}else if(e.getKeyCode()==82) {
			JOptionPane.showMessageDialog(new JFrame(),"Resuming an already saved game.");
		}else if(e.getKeyCode()==49) {
			JOptionPane.showMessageDialog(new JFrame(),"Start a game from level 1.");
		}else if(e.getKeyCode()==50) {
			JOptionPane.showMessageDialog(new JFrame(),"Start a game from level 2.");
		} 
	}
	
	private void CtrlNotPressedActions(KeyEvent e) {
		if(e.getKeyCode()==32) {
			JOptionPane.showMessageDialog(new JFrame(),"Game is paused.");
		}else if(e.getKeyCode()==27) {
			JOptionPane.showMessageDialog(new JFrame(),"Resumes the game.");
		}else if(e.getKeyCode()==37) {
			JOptionPane.showMessageDialog(new JFrame(),"Moving left");
		}else if(e.getKeyCode()==38) {
			JOptionPane.showMessageDialog(new JFrame(),"Moving up");
		}else if(e.getKeyCode()==39) {
			JOptionPane.showMessageDialog(new JFrame(),"Moving right");
		}else if(e.getKeyCode()==40) {
			JOptionPane.showMessageDialog(new JFrame(),"Moving down");
		}
	}
	
	
	
	
	public static void main(String[] args) {
		new GUI();
	}
}
