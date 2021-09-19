package nz.ac.vuw.ecs.swen225.gp21.App;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

public class StartingFrame extends JFrame{
	
	
	private int action = -1;
	
	public StartingFrame(String title) {
		//set name and icon of the frame
				this.setTitle(title);
				ImageIcon logo = new ImageIcon(GUI.class.getResource("chips-challenge-logo.png"));
				this.setIconImage(logo.getImage());
				
				//set size and outline settings
				this.setSize(900,600);
				this.setResizable(false);
				this.setLayout(new GridLayout(2, 1));
				this.getContentPane().setBackground(new Color(0, 120, 0));
				
				//Close operations
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				//Logo
				JLabel logoLabel = new JLabel("");
				ImageIcon mainLogo = new ImageIcon(GUI.class.getResource("MainLogo.png"));
				logoLabel.setIcon(mainLogo);
				logoLabel.setHorizontalAlignment(JLabel.CENTER);
				this.add(logoLabel);
				
				
				JPanel optionsPanel = new JPanel();
				optionsPanel.setBorder(new LineBorder(new Color(0, 120, 0), this.getHeight()/8, false));
				optionsPanel.setLayout(new GridLayout(3, 1));
				JButton startButton = createButton("Start Game", new Color(0, 120, 0), new Color(200, 0, 200),1);
				JButton loadButton = createButton("Continue a saved Game", new Color(0, 120, 0), Color.YELLOW,2);
				JButton infoButton = createButton("More Info", new Color(0, 120, 0), Color.BLACK,3);
				optionsPanel.add(startButton);
				optionsPanel.add(loadButton);
				optionsPanel.add(infoButton);
				optionsPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
				this.add(optionsPanel);
				
				
				
				
				
				
				
				this.setVisible(true);
	}
	
	
	private JButton createButton(String name, Color color1, Color color2, int actionValue) {
		JButton button = new JButton(name);
		button.setFont(new Font("MV Boli", Font.HANGING_BASELINE, 25));
		button.setBorder(new BevelBorder(BevelBorder.RAISED));
		button.setBackground(color1);
		button.setForeground(color2);
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				button.setBackground(color1);
				button.setForeground(color2);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				button.setBackground(color2);
				button.setForeground(color1);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setAction(actionValue);
			}
		});
		return button;
	}
	
	
	
	public static void main(String[] args) {
		new StartingFrame("Chip's Challenge");
	}


	public int getAction() {
		return action;
	}


	public void setAction(int action) {
		this.action = action;
	}
}
