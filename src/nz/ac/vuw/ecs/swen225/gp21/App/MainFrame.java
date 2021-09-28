package nz.ac.vuw.ecs.swen225.gp21.App;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

	
	private JMenuItem saveItem = new JMenuItem("Save the game");
	private JMenuItem loadItem = new JMenuItem("Load a game");
	
	private JMenuItem level1Item = new JMenuItem("Level 1");
	private JMenuItem level2Item = new JMenuItem("Level 2");
	
	
	public MainFrame(String title) {
		//set name and icon of the frame
				this.setTitle(title);
				ImageIcon logo = new ImageIcon(GUI.class.getResource("chips-challenge-logo.png"));
				this.setIconImage(logo.getImage());
				
				//set size and outline settings
				this.setSize(900,600);
				this.setResizable(false);
				this.setLayout(new GridLayout(1, 3));
				this.getContentPane().setBackground(new Color(0, 120, 0));
				
				//Close operations
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
	
	
	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		
		JMenu gameMenu = new JMenu("Game");
		gameMenu.add(saveItem);
		gameMenu.add(loadItem);
		
		
		JMenu levelsMenu = new JMenu("Levels");
		levelsMenu.add(level1Item);
		levelsMenu.add(level2Item);
		
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About Chip's challenge");
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Desktop d = Desktop.getDesktop();
				try {
					d.browse(new URI("https://en.wikipedia.org/wiki/Chip%27s_Challenge#:~:text=The%20premise%20of%20the%20game,very%20exclusive%20Bit%20Busters%20Club."));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JMenuItem shortcutsItem = new JMenuItem("Shortcuts");
		shortcutsItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(new JFrame(), shortcuts);

			}
		});
		helpMenu.add(aboutItem);
		helpMenu.add(shortcutsItem);
		
		menuBar.add(gameMenu);
		menuBar.add(levelsMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);
	}
	
	
	
	
	
	
	public JMenuItem getSaveItem() {
		return saveItem;
	}
	public void setSaveItem(JMenuItem saveItem) {
		this.saveItem = saveItem;
	}

	public JMenuItem getLoadItem() {
		return loadItem;
	}

	public void setLoadItem(JMenuItem loadItem) {
		this.loadItem = loadItem;
	}

	public JMenuItem getLevel1Item() {
		return level1Item;
	}

	public void setLevel1Item(JMenuItem level1Item) {
		this.level1Item = level1Item;
	}
	
	public JMenuItem getLevel2Item() {
		return level2Item;
	}
	public void setLevel2Item(JMenuItem level2Item) {
		this.level2Item = level2Item;
	}
	private String shortcuts = "CTRL + X -> Exit without saving\n"
			+ "CTRL + S -> Save and Exit\n"
			+ "CTRL + 1 -> Start a game from level 1\n"
			+ "CTRL + 2 -> Start a game from level 2\n"
			+ "CTRL + R -> Resume an already saved game\n"
			+ "SPACEBAR -> Pause the game\n"
			+ "ESC -> Resume the game\n"
			+ "KEYBOARD ARROWs -> Move the player";
}
