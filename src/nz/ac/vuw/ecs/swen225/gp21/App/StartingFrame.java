package nz.ac.vuw.ecs.swen225.gp21.App;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

/**
 * StartingFrame is the initial menu which the user can pick
 * an action.
 * These actions include starting from level 1, starting a saved game,
 * and finding more information about the game.

 * @author mofattmoha
 *
 */
public class StartingFrame extends JFrame {

  /**
   * The constructor for this class.
   * All the components of the frame are added here.

   * @param title the window's name
   */
  public StartingFrame(String title) {
    // set name and icon of the frame
    this.setTitle(title);
    ImageIcon logo = new ImageIcon(GUI.class.getResource("chips-challenge-logo.png"));
    this.setIconImage(logo.getImage());

    // set size and outline settings
    this.setSize(900, 600);
    this.setResizable(false);
    this.setLayout(new GridLayout(2, 1));
    this.getContentPane().setBackground(new Color(0, 120, 0));

    // Close operations
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Logo
    JLabel logoLabel = new JLabel("");
    ImageIcon mainLogo = new ImageIcon(GUI.class.getResource("MainLogo.png"));
    logoLabel.setIcon(mainLogo);
    logoLabel.setHorizontalAlignment(JLabel.CENTER);
    this.add(logoLabel);

    JPanel optionsPanel = new JPanel();
    optionsPanel.setBorder(new LineBorder(new Color(0, 120, 0), this.getHeight() / 8, false));
    optionsPanel.setLayout(new GridLayout(3, 1));
    JButton startButton = createButton("Start Game",
        new Color(0, 120, 0), new Color(200, 0, 200), 1);
    JButton loadButton = createButton("Continue a saved Game",
        new Color(0, 120, 0), Color.YELLOW, 2);
    JButton infoButton = createButton("More Info", new Color(0, 120, 0), Color.BLACK, 3);
    optionsPanel.add(startButton);
    optionsPanel.add(loadButton);
    optionsPanel.add(infoButton);
    optionsPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
    this.add(optionsPanel);

    this.setVisible(true);
  }

  /**
   * The required buttons are created using this method.
   * This is to avoid repetition of code.

   * @param name of the buttin
   * @param color1 background color
   * @param color2 font color
   * @param actionValue int showing the action to be taken
   * @return
   */
  private JButton createButton(String name, Color color1, Color color2, int actionValue) {
    JButton button = new JButton(name);
    button.setFont(new Font("MV Boli", Font.HANGING_BASELINE, 25));
    button.setBorder(new BevelBorder(BevelBorder.RAISED));
    button.setBackground(color1);
    button.setForeground(color2);
    JFrame frame = this;
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
        // setAction(actionValue);
        if (actionValue == 1) {
          try {
            frame.setVisible(false);
            frame.dispose();
            new GUI("level1.xml");
          } catch (FontFormatException | IOException | InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        } else if (actionValue == 2) {
          JFileChooser chooser = new JFileChooser();
          chooser.setCurrentDirectory(
              new File("./src/nz/ac/vuw/ecs/swen225/gp21//Persistency/levels/"));
          chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
          chooser.showOpenDialog(null);

          //String path=chooser.getSelectedFile().getAbsolutePath();
          String filename = chooser.getSelectedFile().getName();
          try {
            new GUI(filename);
          } catch (FontFormatException | IOException | InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }

        } else if (actionValue == 3) {
          Desktop d = Desktop.getDesktop();
          try {
            d.browse(new URI(
                "https://en.wikipedia.org/wiki/Chip%27s_Challenge#:~:text=The%20premise%20of%20the%20game,very%20exclusive%20Bit%20Busters%20Club."));
          } catch (IOException | URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }
      }
    });
    return button;
  }

  /**
    * Start of the game is from here.

    * @param args arguments
    * @throws FontFormatException Invalid font
    * @throws IOException Input/output exception
    * @throws InterruptedException When saving the file fails
    */
  public static void main(String[] args)
      throws FontFormatException, IOException, InterruptedException {
    new StartingFrame("Chip's Challenge");
  }
}
