package App;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class GUI {

	JFrame mainFrame;
	int level = 1;
	
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
		
		
		
		mainFrame.add(gameBoard);
		mainFrame.add(levelInfoPanel);
		mainFrame.setVisible(true);
	}
	
	
	
	private int border(int width, int height) {
		int num = width<height? height : width;
		return (int)((0.05)*num);
	}
	
	
	
	
	public static void main(String[] args) {
		new GUI();
	}
}
