package App;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI {


	JFrame mainFrame;
	int level = 1;
	
	public GUI() {
		//set name and icon of the frame
		mainFrame = new MainFrame("Chip's Challenge-Level "+level);
		
		
		//game board panel settings
		JPanel gameBoard = new JPanel();
		gameBoard.setLayout(new GridLayout(9, 9));
		/*JLabel[][] tiles = new JLabel[9][9];
		for(int i=0 ; i<9 ; i++) {
			for(int j=0 ; j<9 ; j++) {
				tiles[i][j] = new JLabel(wall);
				gameBoard.add(tiles[i][j]);
			}
		}*/
		//mainFrame.add(gameBoard);
		
		
		//The game info panel settings
		JPanel levelInfoPanel = new JPanel();
		levelInfoPanel.setLayout(new GridLayout(4, 1));
		
		
		
		mainFrame.setVisible(true);
	}
	
	
	
	
	public static void main(String[] args) {
		new GUI();
	}
}
