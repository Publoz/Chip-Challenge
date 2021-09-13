package nz.ac.vuw.ecs.swen225.gp21.App;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame{

	public MainFrame(String title) {
		//set name and icon of the frame
				this.setTitle(title);
				ImageIcon logo = new ImageIcon(GUI.class.getResource("chips-challenge-logo.png"));
				this.setIconImage(logo.getImage());
				
				//set size and outline settings
				this.setSize(900,600);
				this.setResizable(false);
				this.setLayout(new GridLayout(1, 2));
				this.getContentPane().setBackground(new Color(0, 120, 0));
				//exit on  close
				JFrame frame = this;
				this.addWindowListener(new WindowAdapter() {
				      public void windowClosing(WindowEvent we) {
				        int result = JOptionPane.showConfirmDialog(frame,
				            "Game is not saved. Are you sure you want to exit?", "Exit Confirmation : ",
				            JOptionPane.YES_NO_OPTION);
				        if (result == JOptionPane.YES_OPTION)
				          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				        else if (result == JOptionPane.NO_OPTION)
				          frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				      }
				    });
	}
}
