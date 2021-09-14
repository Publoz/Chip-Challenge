package nz.ac.vuw.ecs.swen225.gp21.App;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class InfoPanel extends JPanel{
	String fontFilename="C:/Users/mjmof/eclipse-workspace/chip-challenge/src/nz/ac/vuw/ecs/swen225/gp21/App/digital-7.ttf";
	

	public InfoPanel(String text, int value) throws FontFormatException, IOException {
		this.setLayout(new GridLayout(2, 1));
		JLabel infoLabel = new JLabel(text);
		infoLabel.setForeground(Color.RED);
		infoLabel.setFont(new Font("MV Boli", Font.HANGING_BASELINE, 20));
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		infoLabel.setVerticalAlignment(JLabel.TOP);
		JPanel valuePanel = new JPanel();
		valuePanel.setSize(this.getWidth(), this.getHeight());
		valuePanel.setLayout(new BorderLayout());
		JTextPane infoValue = new JTextPane();
		infoValue.setText("\t  "+value);
		//infoValue.setSize(this.getWidth()/3, this.getHeight()/3);
		infoValue.setBackground(Color.black);
		infoValue.setForeground(Color.GREEN);
		
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontFilename));
		font = font.deriveFont(Font.BOLD,50);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		
		
		infoValue.setFont(font);
		infoValue.setEditable(false);
		
		valuePanel.add(infoValue, BorderLayout.CENTER);
		
		this.add(infoLabel);
		this.add(valuePanel);
	}
	
	public void updateValue(int value) {
		
	}
}
