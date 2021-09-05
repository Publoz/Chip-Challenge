package App;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainFrame extends JFrame{

	public MainFrame(String title) {
		//set name and icon of the frame
				this.setTitle(title);
				ImageIcon logo = new ImageIcon(GUI.class.getResource("chips-challenge-logo.png"));
				this.setIconImage(logo.getImage());
				
				//set size and outline settings
				this.setSize(800,600);
				this.setResizable(false);
				this.setLayout(new GridLayout(1, 2));
				this.getContentPane().setBackground(new Color(0, 120, 0));
				//exit on  close
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
