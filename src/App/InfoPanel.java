package App;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class InfoPanel extends JPanel{

	public InfoPanel(String text, int value) {
		this.setLayout(new GridLayout(2, 1));
		JLabel infoLabel = new JLabel(text);
		infoLabel.setForeground(Color.RED);
		infoLabel.setFont(new Font("MV Boli", Font.HANGING_BASELINE, 20));
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		infoLabel.setVerticalAlignment(JLabel.TOP);
		JTextPane infoValue = new JTextPane();
		infoValue.setText("\t  "+value);
		infoValue.setSize(this.getWidth()/3, this.getHeight()/3);
		infoValue.setBackground(Color.black);
		infoValue.setForeground(Color.RED);
		infoValue.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 40));
		infoValue.setEditable(false);
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.setAlignmentY(CENTER_ALIGNMENT);
		
		this.add(infoLabel);
		this.add(infoValue);
	}
}
