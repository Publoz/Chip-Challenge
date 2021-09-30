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
/**
 * In this class, the outfit of the mainFrame is generated
 * and more things are added to it in the GUI class.

 * @author mjmof
 *
 */
public class MainFrame extends JFrame {

  //These components have been created as fields to be
  //able to add action listeners to it in the GUI
  private JMenuItem saveItem = new JMenuItem("Save the game");
  private JMenuItem loadItem = new JMenuItem("Load a game");

  private JMenuItem level1Item = new JMenuItem("Level 1");
  private JMenuItem level2Item = new JMenuItem("Level 2");

  /**
   * The constructor for mainFrame.
   * All the main components of the frame are added here
   * @param title Name of the Frame(Game)
   */
  public MainFrame(String title) {
    // set name and icon of the frame
    this.setTitle(title);
    ImageIcon logo = new ImageIcon(GUI.class.getResource("chips-challenge-logo.png"));
    this.setIconImage(logo.getImage());

    // set size and outline settings
    this.setSize(900, 600);
    this.setResizable(false);
    this.setLayout(new GridLayout(1, 3));
    this.getContentPane().setBackground(new Color(0, 120, 0));

    // Close operations
    JFrame frame = this;
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        int result = JOptionPane.showConfirmDialog(frame, "Game is not saved. Are you sure you want to exit?",
            "Exit Confirmation : ", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else if (result == JOptionPane.NO_OPTION)
          frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      }
    });
  }

  /**
   * This method is called from the GUI after all the 
   * menu items have been added to the menu.
   * Action listeners are the main reason for this choice.
   */
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
          d.browse(new URI(
              "https://en.wikipedia.org/wiki/Chip%27s_Challenge#:~:text=The%20premise%20of%20the%20game,very%20exclusive%20Bit%20Busters%20Club."));
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

  /**
   * Getter for the save menu item
   * @return save menu item
   */
  public JMenuItem getSaveItem() {
    return saveItem;
  }

  /**
   * Setter for the save menu item
   * @param saveItem item to be added
   */
  public void setSaveItem(JMenuItem saveItem) {
    this.saveItem = saveItem;
  }

  /**
   * Getter for the load menu item
   * @return load menu item
   */
  public JMenuItem getLoadItem() {
    return loadItem;
  }

  /**
   * Setter for the load menu item
   * @param loadItem item to be added
   */
  public void setLoadItem(JMenuItem loadItem) {
    this.loadItem = loadItem;
  }

  /**
   * Getter for the menu item for loading level1
   * @return menu item for loading level1 
   */
  public JMenuItem getLevel1Item() {
    return level1Item;
  }

  /**
   * Setter for the menu item for loading level1
   * @param level1Item item to be added
   */
  public void setLevel1Item(JMenuItem level1Item) {
    this.level1Item = level1Item;
  }

  /**
   * Getter for the menu item for loading level2
   * @return menu item for loading level2
   */
  public JMenuItem getLevel2Item() {
    return level2Item;
  }

  /**
   * Setter for the menu item for loading level2
   * @param level2Item item to be added
   */
  public void setLevel2Item(JMenuItem level2Item) {
    this.level2Item = level2Item;
  }

  //Shortcuts details
  private String shortcuts = "CTRL + X -> Exit without saving\n" + "CTRL + S -> Save and Exit\n"
      + "CTRL + 1 -> Start a game from level 1\n" + "CTRL + 2 -> Start a game from level 2\n"
      + "CTRL + R -> Resume an already saved game\n" + "SPACEBAR -> Pause the game\n" + "ESC -> Resume the game\n"
      + "KEYBOARD ARROWs -> Move the player";
}
